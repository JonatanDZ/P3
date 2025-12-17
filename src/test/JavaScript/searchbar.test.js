import {stringToColor} from "../../main/resources/static/js/searchbar.js";

describe("stringToColor", () => {

    describe("stringToColor different values", () => {

        test("Generates different colors for different values when receiving different string", () => {
            // The same strings should result in a perfect similarity score
            expect(stringToColor("test")).not.toBe(stringToColor("teste"));
        });

        test("stringToColor is cass sensitive", () => {
            // The same strings should result in a perfect similarity score
            expect(stringToColor("test")).not.toBe(stringToColor("teSt"));
        });

    });

    describe("stringToColor same value", () => {

        test("Generates same color for the same value", () => {
            // The same strings should result in a perfect similarity score
            expect(stringToColor("test")).toBe(stringToColor("test"));
        });

        test("Generates same color for the same value edgeCase", () => {
            // The same strings should result in a perfect similarity score
            expect(stringToColor("TræLaLaLåLø**")).toBe(stringToColor("TræLaLaLåLø**"));
        });

    });

});
