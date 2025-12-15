import { submitForm } from "../../../main/resources/static/js/submitForm.js";
import { poster } from "../../../main/resources/static/js/fetchTool.js";
import { getCurrentEmployee } from "../../../main/resources/static/js/getCurrentEmployee.js";

// MOCK external modules submitForm depends on:
jest.mock("../../../main/resources/static/js/fetchTool.js", () => ({
    poster: jest.fn()
}));

jest.mock("../../../main/resources/static/js/getCurrentEmployee.js", () => ({
    getCurrentEmployee: jest.fn()
}));

// Mock alert to avoid errors
global.alert = jest.fn();


beforeEach(()=>{
    fetch.resetMocks();
    jest.clearAllMocks();
});

describe("submitForm", () => {
    describe("nonDynamic urls", () => {
        beforeEach(() => {
            document.body.innerHTML = `
            <div id="addToolDiv" style="display:none;"></div>
            <button class="toggleBtn"></button>
            <input type="checkbox" id="isPersonal">
            <input id="toolName" value="Test Tool"/>
            <input id="toolURL1" value="http://example."/>
            <b id="toolUser">USER</b>
            <div id="selectedTags" class="selected-tags"><div class="tag-chip" data-tag="3" data-tag-name="Office" style="background-color: rgb(143, 220, 198);"><span>Office</span><button type="button">x</button></div></div>
            <input type="checkbox" id="isDynamic"/>
            <div class="checkBoxDiv"><label>DevOps</label><input type="checkbox" id="DevOpsInput" value="1" class="departmentsChecks" checked></div>
            <div class="checkBoxDiv">
                <label for="stagingInput"> Staging </label>
                <input id="stagingInput" class="stagesChecks" type="checkbox" value="2" name="Staging" checked>
            </div>
            <div class="checkBoxDiv"><label>DK</label><input type="checkbox" id="DKInput" value="1" class="jurisdictionsChecks" checked></div>
            <button id="submitBtn"></button>
        `;
        });

        test("submit form non-dynamic link non personal", async () => {
            fetch.mockResponseOnce(JSON.stringify({ success: true })); // personal tool post

            // Mock required external functions
            poster.mockResolvedValue({ id: 999, is_personal: false });
            getCurrentEmployee.mockResolvedValue({ initials: "USER" });

            await submitForm();

            expect(poster).toHaveBeenCalled();
            expect(fetch).not.toHaveBeenCalled();


            const [url, jsonString] = poster.mock.calls[0];
            const body = JSON.parse(jsonString);

            expect(body.name).toBe("Test Tool");
            expect(body.url).toBe("http://example.");
            expect(body.is_dynamic).toBe(false);
            expect(body.is_personal).toBe(false);
            expect(body.tags).toEqual([{ id: "3" }]);
            expect(body.departments).toEqual([{ id: "1" }]);
            expect(body.jurisdictions).toEqual([{ id: "1" }]);
            expect(body.stages).toEqual([{ id: "2" }]);
        });

        test("submit form non-dynamic link personal", async () => {
            fetch.mockResponseOnce(JSON.stringify({ success: true })); // personal tool post

            document.querySelector("#isPersonal").checked = true;

            // Mock required external functions
            poster.mockResolvedValue({ id: 999, is_personal: true });
            getCurrentEmployee.mockResolvedValue({ initials: "USER" });

            await submitForm();

            expect(poster).toHaveBeenCalled();
            expect(fetch).toHaveBeenCalled();

            const [url, jsonString] = poster.mock.calls[0];
            const body = JSON.parse(jsonString);

            expect(body.name).toBe("Test Tool");
            expect(body.url).toBe("http://example.");
            expect(body.is_dynamic).toBe(false);
            expect(body.is_personal).toBe(true);
            expect(body.jurisdictions).toEqual([{ id: "1" }]);
            expect(body.stages).toEqual([{ id: "2" }]);
        });

        test("submit form non-dynamic link no toolName", async () => {
            fetch.mockResponseOnce(JSON.stringify({ success: true })); // personal tool post

            document.querySelector("#toolName").value = "";

            // Mock required external functions
            poster.mockResolvedValue({ id: 999, is_personal: true });
            getCurrentEmployee.mockResolvedValue({ initials: "USER" });

            await expect(submitForm()).rejects.toThrow('Tool name cannot be empty');
        });

        test("submit form non-dynamic link no url", async () => {
            fetch.mockResponseOnce(JSON.stringify({ success: true })); // personal tool post

            document.querySelector("#toolURL1").value = "";

            // Mock required external functions
            poster.mockResolvedValue({ id: 999, is_personal: true });
            getCurrentEmployee.mockResolvedValue({ initials: "USER" });

            await expect(submitForm()).rejects.toThrow('URL cannot be empty');
        });
    });

    //------------Dynamic URL------------

    describe("Dynamic urls", () => {
        beforeEach(() => {
            document.body.innerHTML = `
            <div id="addToolDiv" style="display:none;"></div>
            <button class="toggleBtn"></button>
            <input type="checkbox" id="isPersonal">
            <input id="toolName" value="Test Tool"/>
            <input id="toolURL1" value="http://example."/>
            <b id="toolUser">USER</b>
            <input id="toolURL2" value=".com"/>
            <div id="selectedTags" class="selected-tags"><div class="tag-chip" data-tag="3" data-tag-name="Office" style="background-color: rgb(143, 220, 198);"><span>Office</span><button type="button">x</button></div></div>
            <input type="checkbox" id="isDynamic" checked/>
            <div class="checkBoxDiv"><label>DevOps</label><input type="checkbox" id="DevOpsInput" value="1" class="departmentsChecks" checked></div>
            <div class="checkBoxDiv">
                <label for="stagingInput"> Staging </label>
                <input id="stagingInput" class="stagesChecks" type="checkbox" value="2" name="Staging" checked>
            </div>
            <div class="checkBoxDiv"><label>DK</label><input type="checkbox" id="DKInput" value="1" class="jurisdictionsChecks" checked></div>
            <button id="submitBtn"></button>
        `;
        });

        test("submit form dynamic link non personal", async () => {
            fetch.mockResponseOnce(JSON.stringify({ success: true })); // personal tool post

            // Mock required external functions
            poster.mockResolvedValue({ id: 999, is_personal: false });
            getCurrentEmployee.mockResolvedValue({ initials: "USER" });

            await submitForm();

            expect(poster).toHaveBeenCalled();
            expect(fetch).not.toHaveBeenCalled();


            const [url, jsonString] = poster.mock.calls[0];
            const body = JSON.parse(jsonString);

            expect(body.name).toBe("Test Tool");
            expect(body.url).toBe("http://example.$USER$.com");
            expect(body.is_dynamic).toBe(true);
            expect(body.is_personal).toBe(false);
            expect(body.tags).toEqual([{ id: "3" }]);
            expect(body.departments).toEqual([{ id: "1" }]);
            expect(body.jurisdictions).toEqual([{ id: "1" }]);
            expect(body.stages).toEqual([{ id: "2" }]);
        });

        test("submit form dynamic link no toolName", async () => {
            fetch.mockResponseOnce(JSON.stringify({ success: true })); // personal tool post

            document.querySelector("#toolName").value = "";

            // Mock required external functions
            poster.mockResolvedValue({ id: 999, is_personal: true });
            getCurrentEmployee.mockResolvedValue({ initials: "USER" });

            await expect(submitForm()).rejects.toThrow('Tool name cannot be empty');
        });

        test("submit form dynamic link no url", async () => {
            fetch.mockResponseOnce(JSON.stringify({ success: true })); // personal tool post

            document.querySelector("#toolURL1").value = "";
            document.querySelector("#toolURL2").value = "";


            // Mock required external functions
            poster.mockResolvedValue({ id: 999, is_personal: true });
            getCurrentEmployee.mockResolvedValue({ initials: "USER" });

            await expect(submitForm()).rejects.toThrow('Dynamic URL part cannot be empty');
        });
    });



});

describe("submitTag", () => {
    let submitTag, poster, addTagChip;

    beforeEach(() => {
        jest.resetModules();
        jest.clearAllMocks();

        // MOCKS, doing this to avoid changes in the DOM
        jest.doMock("../../../main/resources/static/js/fetchTool.js", () => ({
            poster: jest.fn()
        }));

        jest.doMock("../../../main/resources/static/js/loadOptions.js", () => ({
            addTagChip: jest.fn()
        }));

        document.body.innerHTML = `<input id="tags" value="NewTag" />`;

        // import after mock (test)
        submitTag = require("../../../main/resources/static/js/submitForm.js").submitTag;
        poster = require("../../../main/resources/static/js/fetchTool.js").poster;
        addTagChip = require("../../../main/resources/static/js/loadOptions.js").addTagChip;
    });

    test("submitTag works", async () => {
        poster.mockResolvedValue({ id: 99, name: "NewTag" });

        await submitTag();

        expect(poster).toHaveBeenCalledWith(
            "tags",
            JSON.stringify({ value: "NewTag" })
        );

        expect(addTagChip).toHaveBeenCalledWith({ id: 99, name: "NewTag" });
    });

    test("submitTag submits when input is empty", async () => {
        document.querySelector("#tags").value = "";

        poster.mockResolvedValue({ id: 1, name: "" });

        await expect(submitTag()).rejects.toThrow('Tag value cannot be empty');

    });
});
