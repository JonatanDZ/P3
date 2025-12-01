import { displayReview, clearDiv, createParagraph } from "../../../main/resources/static/js/toggleForm.js";

function setupDOM() {
    // Did this DOM structure so the tests run in a predictable environment.
    // The mock summary
    document.body.innerHTML = `
    <input id="toolName" value="Test Tool" />
    <input id="toolURL1" value="https://example.com/" />
    <input id="toolURL2" value="/end" />
    <input id="isDynamic" type="checkbox" />
    <input id="isPersonal" type="checkbox" />

    <div class="tool-summary"></div>

    <label>
        <input class="jurisdictionsChecks" type="checkbox" checked />
        <span>DK</span>
    </label>

    <label>
        <input class="stagesChecks" type="checkbox" name="stage" checked />
        <span>stage</span>
    </label>

    <label>
        <input class="departmentsChecks" type="checkbox" checked />
        <span>Frontend</span>
    </label>

    <span class="tag-chip" data-tag-name="JEST"></span>
    <span class="tag-chip" data-tag-name="Swagger"></span>
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
        "URL: https://example.com/{initials}/end"
    );
});

// Ensures a static URL is used when isDynamic is not checked.
test("Generate correct static URL", () => {
    displayReview(); // isDynamic = false

    const summary = document.querySelector(".tool-summary");
    const urlParagraph = [...summary.querySelectorAll("p")]
        .find(p => p.textContent.startsWith("URL:"));

    expect(urlParagraph.textContent).toBe("URL: https://example.com/");
});

// When personal mode is enabled, only personal-related fields should show.
test("is_personal only shows personal-items", () => {
    document.querySelector("#isPersonal").checked = true;

    displayReview();

    const summary = document.querySelector(".tool-summary");
    const textList = [...summary.querySelectorAll("p")].map(p => p.textContent);

    expect(textList).toContain("Personal: Yes");
    expect(textList).toContain("Name: Test Tool");
    expect(textList).toContain("URL: https://example.com/");
    expect(textList).toContain("Jurisdiction: ");
    expect(textList).toContain("Stage: stage");

    // The following should NOT appear in personal mode.
    expect(textList.some(t => t.startsWith("Tags"))).toBe(false);
    expect(textList.some(t => t.startsWith("Departments"))).toBe(false);
    expect(textList.some(t => t.startsWith("Dynamic"))).toBe(false);
});

// Verifies that non‑personal mode displays all relevant fields.
test("Non-personal mode shows all items", () => {
    document.querySelector("#isPersonal").checked = false;
    document.querySelector("#isDynamic").checked = false;

    displayReview();

    const summary = document.querySelector(".tool-summary");
    const textList = [...summary.querySelectorAll("p")].map(p => p.textContent);

    expect(textList).toContain("Personal: No");
    expect(textList).toContain("Name: Test Tool");
    expect(textList).toContain("Dynamic: No");
    expect(textList).toContain("URL: https://example.com/");
    expect(textList).toContain("Departments: ");
    expect(textList).toContain("Jurisdiction: ");
    expect(textList).toContain("Stage: stage");
    expect(textList).toContain("Tags: JEST, Swagger");
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
