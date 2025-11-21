import { toggleCards } from "../../../main/resources/static/js/toggleForm.js";
import { updateAllowedCards } from "../../../main/resources/static/js/addToolForm.js";

jest.mock("../../../main/resources/static/js/addToolForm.js", () => ({
    updateAllowedCards: jest.fn()
}));

beforeEach(() => {
    document.body.innerHTML = `
        <button id="prev"></button>
        <button id="next"></button>

        <div id="formCard1" class="formCards" style="display:none;"></div>
        <div id="formCard3" class="formCards" style="display:none;"></div>
        <div id="formCard5" class="formCards" style="display:none;"></div>
    `;

    // Mock allowed cards
    window.allowedCards = [1, 3, 5];
    global.allowedCards = [1, 3, 5];

    updateAllowedCards.mockImplementation(() => {
        window.allowedCards = [1, 3, 5];
        global.allowedCards = [1, 3, 5];
    });
});

describe("toggleCards", () => {

    test("calls updateAllowedCards()", () => {
        toggleCards(1, "1");
        expect(updateAllowedCards).toHaveBeenCalled();
    });

    test("shows the next formCard correctly", () => {
        const newCard = toggleCards(1, 1); // from card 1 -> 3

        expect(newCard).toBe(3);

        expect(document.querySelector("#formCard1").style.display).toBe("none");
        expect(document.querySelector("#formCard3").style.display).toBe("block");
        expect(document.querySelector("#formCard5").style.display).toBe("none");
    });

    test("shows the previous formCard correctly", () => {
        const newCard = toggleCards(-1, 3); // 3 -> 1

        expect(newCard).toBe(1);

        expect(document.querySelector("#formCard1").style.display).toBe("block");
        expect(document.querySelector("#formCard3").style.display).toBe("none");
        expect(document.querySelector("#formCard5").style.display).toBe("none");
    });
});
