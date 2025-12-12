import { displayReview, clearDiv, createParagraph } from "../../../main/resources/static/js/toggleForm.js";

function setupDOM() {
    // Did this DOM structure so the tests run in a predictable environment.
    // The mock summary
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
        <div class="tool-summary">
        </div>
    `;
}

beforeEach(() => {
    setupDOM(); // // Reset DOM before each test so tests dont get mixed up.
});

// Checks that the URL is generated with dynamic initials placeholder.
test("Generate correct dynamic URL", () => {
    document.querySelector("#isDynamic").checked = true;

    displayReview();

    const summary = document.querySelector(".tool-summary");
    const urlParagraph = [...summary.querySelectorAll("p")]
        .find(p => p.textContent.startsWith("URL:"));

    expect(urlParagraph.textContent).toBe(
        "URL: http://example.{initials}.com"
    );
});

// Ensures a static URL is used when isDynamic is not checked.
test("Generate correct static URL", () => {
    document.querySelector("#toolURL1").value = "https://example.com/";
    displayReview(); // isDynamic = false

    const summary = document.querySelector(".tool-summary");
    const urlParagraph = [...summary.querySelectorAll("p")]
        .find(p => p.textContent.startsWith("URL:"));

    expect(urlParagraph.textContent).toBe("URL: https://example.com/");
});

// When personal mode is enabled, only personal-related fields should show.
test("is_personal only shows personal-items", () => {
    document.querySelector("#isPersonal").checked = true;
    document.querySelector("#toolURL1").value = "https://example.com/";


    displayReview();

    const summary = document.querySelector(".tool-summary");
    const textList = [...summary.querySelectorAll("p")].map(p => p.textContent);

    expect(textList).toContain("Personal: Yes");
    expect(textList).toContain("Name: Test Tool");
    expect(textList).toContain("URL: https://example.com/");
    expect(textList).toContain("Jurisdiction: ");
    expect(textList).toContain("Stage: Staging");

    // The following should NOT appear in personal mode.
    expect(textList.some(t => t.startsWith("Tags"))).toBe(false);
    expect(textList.some(t => t.startsWith("Departments"))).toBe(false);
    expect(textList.some(t => t.startsWith("Dynamic"))).toBe(false);
});

// Verifies that non‑personal mode displays all relevant fields.
test("Non-personal mode shows all items", () => {
    document.querySelector("#isPersonal").checked = false;
    document.querySelector("#isDynamic").checked = false;
    document.querySelector("#toolURL1").value = "https://example.com/";

    displayReview();

    const summary = document.querySelector(".tool-summary");
    const textList = [...summary.querySelectorAll("p")].map(p => p.textContent);

    expect(textList).toContain("Personal: No");
    expect(textList).toContain("Name: Test Tool");
    expect(textList).toContain("Dynamic: No");
    expect(textList).toContain("URL: https://example.com/");
    expect(textList).toContain("Departments: ");
    expect(textList).toContain("Jurisdiction: ");
    expect(textList).toContain("Stage: Staging");
    expect(textList).toContain("Tags: Office");
});

// Ensures clearDiv wipes the container clean.
test("clearDiv removes all content", () => {
    const div = document.querySelector(".tool-summary");

    div.innerHTML = "<p>Old item</p><p>Another</p>";

    clearDiv(div);

    expect(div.children.length).toBe(0);
});

// Ensures createParagraph generates a correct <p> element with text.
test("createParagraph creates correct p-element", () => {
    const div = document.querySelector(".tool-summary");

    createParagraph(div, "Title", "Value");

    const p = div.querySelector("p");
    expect(p).not.toBeNull();
    expect(p.textContent).toBe("Title: Value");
});
