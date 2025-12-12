import { fuzzySearch } from "../../../main/resources/static/js/fuzzySearch.js";

describe("fuzzySearch()", () => {

    //Test tool
    const tool = {
        name: "Swagger",
        url: "https://example.com/tool",
        tags: ["api", "documentation", "openapi", "devtools"],
        is_personal: false
    };

    test("Exact match on name should return true", () => {
        expect(fuzzySearch("Swagger", tool)).toBe(true);
    });

    test("Lowcase match on name should return true", () => {
        expect(fuzzySearch("swagger", tool)).toBe(true);
    });

    //'swager' should be considered similar enough to 'swagger' (misspelling)
    test("Misspelling 'swager' should still match the tool name and return true", () => {
        expect(fuzzySearch("swager", tool)).toBe(true);
    });

    // Short strings usually give low similarity; 'swa' should not match 'swagger'
    test("'wag' is too short to match 'swagger' and should return false", () => {
        expect(fuzzySearch("wag", tool)).toBe(false);
    });

    // 'eksempel' is part of the URL, but according to similarity logic it does not pass the 0.30
    test("'eksempel' should NOT match the URL because similarity stays below the 0.30", () => {
        expect(fuzzySearch("eksempel", tool)).toBe(false);
    });

    // 'api' is an exact tag and should match
    test("Exact tag 'api' should return true", () => {
        expect(fuzzySearch("api", tool)).toBe(true);
    });

    // 'doc' should not be similar enough to 'documentation'
    test("'dok' is too short to match the tag 'documentation' and should return false", () => {
        expect(fuzzySearch("dok", tool)).toBe(false);
    });

    test("'openap' should match the tag 'openapi' because similarity is high", () => {
        expect(fuzzySearch("openap", tool)).toBe(true);
    });

    // Completely unrelated input
    test("Completely unrelated input ('banana') should return false", () => {
        expect(fuzzySearch("banana", tool)).toBe(false);
    });
});
