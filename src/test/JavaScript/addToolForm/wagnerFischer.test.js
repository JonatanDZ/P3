import {wagnerFischer} from "../../../main/resources/static/js/fuzzySearch.js";

describe("wagnerFischer", () => {

    test("identical strings to similarity 1", () => {
        expect(wagnerFischer("test", "test")).toBe(1);
    });

    test("completely different strings to low similarity", () => {
        const score = wagnerFischer("abc", "xyz");
        expect(score).toBeLessThan(0.2);
    });

    test("one empty string", () => {
        expect(wagnerFischer("", "test")).toBe(0);
        expect(wagnerFischer("test", "")).toBe(0);
    });

    test("small typo gives high similarity", () => {
        expect(wagnerFischer("helo", "hello")).toBeGreaterThan(0.6);
    });

    test("case differences should not matter", () => {
        expect(wagnerFischer("Hello".toLowerCase(), "hello")).toBe(1);
    });
});
