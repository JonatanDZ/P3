import { displayTools } from "../../main/resources/static/js/displayTools.js";
import { isToolInFavorite } from "../../main/resources/static/js/isToolInFavorite.js";

jest.mock('../../main/resources/static/js/isToolInFavorite.js', () => ({
    isToolInFavorite: jest.fn()
}));

describe("displayTool()", () => {
    beforeEach(() => {
        jest.clearAllMocks();

        document.body.innerHTML = `
            <ul id="allTools"></ul>
        `;
    });
    test("DisplayTool test: Displaying two tools", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            {
                id: 1,
                name: "Tintin",
                url: "https://google.com",
                tags: ["Dev", "Stage"]
            },
            {
                id: 2,
                name: "Bobski",
                url: "https://github.com",
                tags: ["Prod"]
            }
            ];

        isToolInFavorite.mockResolvedValue(false);

        await displayTools(mockTools, mockList);

        expect(mockList.children.length).toBe(2);

        const firstTool = mockList.children[0];
        expect(firstTool.querySelector(".tool-name").textContent).toBe("Tintin");
        expect(firstTool.querySelector(".tool-url").textContent).toBe("https://google.com");
        expect(firstTool.querySelectorAll(".tag").length).toBe(2);
        expect(firstTool.querySelector(".star").textContent).toBe("☆");

        const secondTool = mockList.children[1];
        expect(secondTool.querySelector(".tool-name").textContent).toBe("Bobski");
        expect(secondTool.querySelector(".tool-url").textContent).toBe("https://github.com");
        expect(secondTool.querySelectorAll(".tag").length).toBe(1);
        expect(secondTool.querySelector(".star").textContent).toBe("☆");
    });

    test("DisplayTool test: Favorite tool", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            {
                id: 1,
                name: "Tintin",
                url: "https://google.com",
                tags: ["Dev", "Stage"]
            }
        ];

        isToolInFavorite.mockResolvedValue(true);

        await displayTools(mockTools, mockList);

        expect(mockList.children.length).toBe(1);

        const firstTool = mockList.children[0];
        expect(firstTool.querySelector(".tool-name").textContent).toBe("Tintin");
        expect(firstTool.querySelector(".tool-url").textContent).toBe("https://google.com");
        expect(firstTool.querySelectorAll(".tag").length).toBe(2);
        expect(firstTool.querySelector(".star").textContent).toBe("★");
    });

    test("DisplayTool test: Empty tool", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            {

            }
        ];

        isToolInFavorite.mockResolvedValue(false);

        await displayTools(mockTools, mockList);

        expect(mockList.children.length).toBe(1);

        const firstTool = mockList.children[0];
        expect(firstTool.querySelector(".tool-name").textContent).toBe("");
        expect(firstTool.querySelector(".tool-url").textContent).toBe("");
        expect(firstTool.querySelectorAll(".tag").length).toBe(0);
        expect(firstTool.querySelector(".star").textContent).toBe("☆");
    });

    test("DisplayTool test: Change favorite", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            { id: '1', name: '', url: '', tags: [] }
        ];

        isToolInFavorite.mockResolvedValue(false);

        await displayTools(mockTools, mockList);

        expect(mockList.children.length).toBe(1);

        const firstTool = mockList.children[0];
        const starSpan = firstTool.querySelector(".star");
        const starBtn = firstTool.querySelector(".star-button");

        expect(starSpan.textContent).toBe("☆");

        // Simulate click
        await starBtn.click();

        // Wait a tick for async
        await new Promise(process.nextTick);

        expect(starSpan.textContent).toBe("★");
    });


});