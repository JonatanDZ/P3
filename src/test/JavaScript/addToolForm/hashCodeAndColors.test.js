import { stringToColor } from "../../../main/resources/static/js/searchbar.js";

describe("stringToColor", () => {

    test("stringToColor should return a valid rgb() color", () => {
        const color = stringToColor("Swagger");

        // Accept integer or floating numbers
        expect(color).toMatch(/^rgb\(\d+(\.\d+)?,\d+(\.\d+)?,\d+(\.\d+)?\)$/);
    });

    test("same input gives same color", () => {
        const c1 = stringToColor("world");
        const c2 = stringToColor("world");
        expect(c1).toBe(c2);
    });

    test("different inputs give different colors", () => {
        const c1 = stringToColor("a");
        const c2 = stringToColor("b");
        expect(c1).not.toBe(c2);
    });

    test("handles empty string input", () => {
        const color = stringToColor("");
        expect(color).toMatch(/^rgb\(\d+,\d+,\d+\)$/);
    });
});
