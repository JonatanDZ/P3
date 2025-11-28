import { formToJSON } from "../../../main/resources/static/js/submitForm.js";
import { addTagChip } from "../../../main/resources/static/js/loadOptions.js";

describe("formToJSON – tags handling", () => {

    beforeEach(() => {
        // The mock tag form
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

    // Test if the function handles the empty tag
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

    test("removing tag chip unchecks checkbox and updates JSON", async () => {
        // Add a chip using the real function
        const tag = { id: 6, value: "Al-Qaeda" };

        // Reset DOM to only one chip to simplify test
        document.body.innerHTML = `
            <div id="selectedTags"></div>

            <div class="checkBoxDiv">
                <label>Al-Qaeda</label>
                <input type="checkbox" class="tagChecks" checked>
            </div>

            <input id="isPersonal" type="checkbox"/>
            <input id="toolName" value="X"/>
            <input id="toolURL1" value="URL"/>
            <input id="isDynamic" type="checkbox"/>
        `;

        // Add chip the same way the user would
        addTagChip(tag);

        const chip = document.querySelector(".tag-chip");
        expect(chip).not.toBeNull();

        // Click remove button, triggers uncheckTag
        chip.querySelector("button").click();

        // Checkbox should now be unchecked
        const checkbox = document.querySelector(".tagChecks");
        expect(checkbox.checked).toBe(false);

        // formToJSON should now return no tags
        const json = await formToJSON();
        const parsed = JSON.parse(json);

        expect(parsed.tags).toEqual([]);
    });
});
