import { submitForm } from "../../../main/resources/static/js/submitForm.js";

// MOCK external modules submitForm depends on:
jest.mock("../../../main/resources/static/js/fetchTool.js", () => ({
    poster: jest.fn()
}));

jest.mock("../../../main/resources/static/js/getCurrentEmployee.js", () => ({
    getCurrentEmployee: jest.fn()
}));

import { poster } from "../../../main/resources/static/js/fetchTool.js";
import { getCurrentEmployee } from "../../../main/resources/static/js/getCurrentEmployee.js";

// Mock alert to avoid errors
global.alert = jest.fn();


beforeEach(() => {
    fetch.resetMocks();
    jest.clearAllMocks();

    document.body.innerHTML = `
        <div id="addToolDiv" style="display:none;"></div>
        <button class="toggleBtn"></button>
        <input type="checkbox" id="isPersonal">
        <input id="toolName" value="Test Tool"/>
        <input id="toolURL1" value="http://example."/>
        <b id="toolUser">USER</b>
        <input id="toolURL2" value=".com"/>
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

    // Mock required external functions
    poster.mockResolvedValue({ id: 999, is_personal: false });
    getCurrentEmployee.mockResolvedValue({ initials: "USER" });
});

describe("submitForm", () => {
    test("submit form non-dynamic link", async () => {
        fetch.mockResponseOnce(JSON.stringify({ success: true })); // personal tool post

        await submitForm();

        expect(poster).toHaveBeenCalled();
        expect(fetch).not.toHaveBeenCalled();


        const [url, jsonString] = poster.mock.calls[0];
        const body = JSON.parse(jsonString);


        expect(body.name).toBe("Test Tool");
        expect(body.url).toBe("http://example.");
        expect(body.is_dynamic).toBe(false);
        expect(body.tags).toEqual([{ id: "3" }]);
        expect(body.departments).toEqual([{ id: "1" }]);
        expect(body.jurisdictions).toEqual([{ id: "1" }]);
        expect(body.stages).toEqual([{ id: "2" }]);
    });

    test("submit form dynamic link 2", async () => {
        // Make tool dynamic
        document.querySelector("#isDynamic").checked = true;

        // Make tool personal so fetch() is triggered
        poster.mockResolvedValue({ id: 999, is_personal: true });

        // Mock getCurrentEmployee() since fetch URL uses initials
        getCurrentEmployee.mockResolvedValue({ initials: "USER" });

        // Mock the fetch that should be triggered for personal tools
        fetch.mockResponseOnce(JSON.stringify({ success: true }));

        await submitForm();

        // NOW fetch must have been called
        expect(fetch).toHaveBeenCalled();

        const [url, options] = fetch.mock.calls[0];

        // This fetch has NO BODY because your code does:
        // fetch(`employee/${initials}/favorites/${toolId}`, { method: "POST" })
        // so body is undefined
        // So we DON'T parse a body here
        expect(url).toBe("employee/USER/favorites/999");

        // Instead we test the dynamic URL inside JSON sent to poster:
        const [posterUrl, posterJson] = poster.mock.calls[0];
        const body = JSON.parse(posterJson);

        expect(body.url).toBe("http://example.$USER$.com");
        expect(body.is_dynamic).toBe(true);
    });
});





describe('submitForm', () => {
    beforeEach(() => {
        jest.clearAllMocks();
//Data used for the tests (simply hard coded HTML)
        document.body.innerHTML = `
        <div id="addToolDiv" style="display:none;"></div>
        <button class="toggleBtn"></button>
        <input type="checkbox" id="isPersonal">
        <input id="toolName" value="Test Tool"/>
        <input id="toolURL1" value="http://example."/>
        <b id="toolUser">USER</b>
        <input id="toolURL2" value=".com"/>
        <div class="tag-chip" data-tag="6" data-tag-name="Al-Qaeda"><span>Al-Qaeda</span><button type="button">x</button></div>
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
    test('submit form non dynamic link', async () => {


        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        }
//Uses submitForm on the hard coded HTML to make it "visible" for jest
        global.fetch.mockResolvedValue(mockResponse);
        await submitForm();

        expect(global.fetch).toHaveBeenCalled();

        const [url, options] = global.fetch.mock.calls[0]; // first call

        const body = JSON.parse(options.body);

        expect(body.name).toBe('Test Tool');
        expect(body.url).toBe('http://example.');
        expect(body.tags).toEqual([{"id":"6"}]);
        expect(body.is_dynamic).toBe(false);
        expect(body.departments).toEqual([{"id":"1"}]);
        expect(body.jurisdictions).toEqual([{"id":"1"}]);
        expect(body.stages).toEqual([{"id":"2"}]);
    })
    test('submit form dynamic link', async () => {


        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        }
        document.querySelector("#isPersonal").checked = false;
        document.querySelector("#isDynamic").checked = true;

        global.fetch.mockResolvedValue(mockResponse);
        await submitForm();

        expect(global.fetch).toHaveBeenCalled();

        const [url, options] = global.fetch.mock.calls[0]; // first call

        const body = JSON.parse(options.body);

        expect(body.name).toBe('Test Tool');
        expect(body.url).toBe('http://example.USER.com');
        expect(body.is_dynamic).toBe(true);
        expect(body.tags).toEqual([{"id":"6"}]);
        expect(body.departments).toEqual([{"id":"1"}]);
        expect(body.jurisdictions).toEqual([{"id":"1"}]);
        expect(body.stages).toEqual([{"id":"2"}]);
    })
    test('submit form when isPersonal is checked', async () => {

        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        };

        document.querySelector("#isPersonal").checked = true;

        global.fetch.mockResolvedValue(mockResponse);
        await submitForm();

        const [url, options] = global.fetch.mock.calls[0];
        const body = JSON.parse(options.body);

        expect(body.is_personal).toBe(true);
    });

    test("submitForm submits even when toolName is empty", async () => {
        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        };

        global.fetch.mockResolvedValue(mockResponse);
        document.querySelector("#toolName").value = ""; // edge case

        await submitForm();

        expect(global.fetch).toHaveBeenCalledTimes(1);

        const [url, options] = global.fetch.mock.calls[0];
        const body = JSON.parse(options.body);

        expect(body.name).toBe("");
        expect(body.url).toBe("http://example.");
        expect(body.is_dynamic).toBe(false);
        expect(body.tags).toEqual([{ id: "6" }]);
        expect(body.departments).toEqual([{ id: "1" }]);
        expect(body.stages).toEqual([{ id: "2" }]);
        expect(body.jurisdictions).toEqual([{ id: "1" }]);
    });

    test("submitForm works when no checkboxes are selected", async () => {
        document.querySelectorAll(".departmentsChecks").forEach(e => e.checked = false);
        document.querySelectorAll(".stagesChecks").forEach(e => e.checked = false);
        document.querySelectorAll(".jurisdictionsChecks").forEach(e => e.checked = false);

        mockResponse = { ok: true, json: async () => ({ success: true }) };
        global.fetch.mockResolvedValue(mockResponse);

        await submitForm();

        const [url, options] = global.fetch.mock.calls[0];
        const body = JSON.parse(options.body);

        expect(body.departments).toEqual([]);
        expect(body.stages).toEqual([]);
        expect(body.jurisdictions).toEqual([]);
    });

    test("submitForm works with no tags selected", async () => {
        document.querySelector(".tag-chip").remove();

        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        };

        global.fetch.mockResolvedValue(mockResponse);
        await submitForm();

        const [url, options] = global.fetch.mock.calls[0];
        const body = JSON.parse(options.body);

        expect(body.tags).toEqual([]);
    });

    test("submitForm handles empty toolURL1", async () => {
        mockResponse = { ok: true, json: async () => ({ success: true }) };
        global.fetch.mockResolvedValue(mockResponse);

        document.querySelector("#toolURL1").value = "";  // edge case

        await submitForm();

        expect(global.fetch).toHaveBeenCalledTimes(1);

        const [, options] = global.fetch.mock.calls[0];
        const body = JSON.parse(options.body);

        expect(body.url).toBe("");
    });

    test("submitForm handles empty toolURL2", async () => {
        mockResponse = { ok: true, json: async () => ({ success: true }) };
        global.fetch.mockResolvedValue(mockResponse);

        document.querySelector("#toolURL2").value = ""; // edge case

        await submitForm();

        const [, options] = global.fetch.mock.calls[0];
        const body = JSON.parse(options.body);

        // expected: "http://example."
        expect(body.url).toBe("http://example.");
    });

    test("submitForm handles both URL parts empty", async () => {
        mockResponse = { ok: true, json: async () => ({ success: true }) };
        global.fetch.mockResolvedValue(mockResponse);

        document.querySelector("#toolURL1").value = "";
        document.querySelector("#toolURL2").value = "";

        await submitForm();

        const [, options] = global.fetch.mock.calls[0];
        const body = JSON.parse(options.body);

        // Empty string
        expect(body.url).toBe("");
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

        await submitTag();

        expect(poster).toHaveBeenCalledTimes(1);
        expect(poster).toHaveBeenCalledWith(
            "tags",
            JSON.stringify({ value: "" })
        );

        expect(addTagChip).toHaveBeenCalledTimes(1);
        expect(addTagChip).toHaveBeenCalledWith({ id: 1, name: "" });
    });
});
