import { formToJSON } from "../../../main/resources/static/js/submitForm.js";

describe("formToJSON – tags handling", () => {

    beforeEach(() => {
        document.body.innerHTML = `
            <div id="selectedTags">
                <div class="tag-chip" data-tag="6" data-tag-name="Al-Qaeda"></div>
                <div class="tag-chip" data-tag="12" data-tag-name="Susan k"></div>
            </div>

            <input id="isPersonal" type="checkbox" />
            <input id="toolName" value="X"/>
            <input id="toolURL1" value="URL"/>
            <input id="isDynamic" type="checkbox"/>
        `;
    });

    test("collects tag chips into correct JSON structure", async () => {
        const json = await formToJSON();
        const parsed = JSON.parse(json);

        expect(parsed.tags).toEqual([
            { id: "6" },
            { id: "12" }
        ]);
    });

    test("handles empty tags correctly", async () => {
        document.body.innerHTML = `
            <div id="selectedTags"></div>

            <input id="isPersonal" type="checkbox" />
            <input id="toolName" value="X"/>
            <input id="toolURL1" value="URL"/>
            <input id="isDynamic" type="checkbox"/>
        `;

        const json = await formToJSON();
        const parsed = JSON.parse(json);

        expect(parsed.tags).toEqual([]);
    });
});
