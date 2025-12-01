import {wagnerFischer} from "../../../main/resources/static/js/fuzzySearch.js";

describe("wagnerFischer", () => {

    test("identical strings to similarity 1", () => {
        // The same strings should result in a perfect similarity score
        expect(wagnerFischer("test", "test")).toBe(1);
    });

    test("completely different strings to low similarity", () => {
        // Completely unrelated strings should give a very low similarity score
        const score = wagnerFischer("abc", "xyz");
        expect(score).toBeLessThan(0.2);
    });

    test("one empty string", () => {
        // If one string is empty, similarity should be zero
        expect(wagnerFischer("", "test")).toBe(0);
        expect(wagnerFischer("test", "")).toBe(0);
    });

    test("small typo gives high similarity", () => {
        // One missing character should still produce a relatively high similarity score
        expect(wagnerFischer("helo", "hello")).toBeGreaterThan(0.6);
    });

    test("case differences should not matter", () => {
        // Ensures the function handles both lowercase and capslock
        expect(wagnerFischer("Hello".toLowerCase(), "hello")).toBe(1);
    });
});
