import {displayTools} from "../../main/resources/static/js/displayTools.js";
import {isToolInFavorite} from "../../main/resources/static/js/isToolInFavorite.js";
import {getCurrentEmployee} from "../../main/resources/static/js/getCurrentEmployee.js";
import { displayFavorites } from "../../main/resources/static/js/displayFavorites.js";

jest.mock('../../main/resources/static/js/isToolInFavorite.js', () => ({
    isToolInFavorite: jest.fn()
}));

jest.mock('../../main/resources/static/js/getCurrentEmployee.js', () => ({
    getCurrentEmployee: jest.fn()
}));

jest.mock('../../main/resources/static/js/displayFavorites.js', () => ({
    displayFavorites: jest.fn()
}));

// Mock fetch globally so the click handler's fetch call doesn't blow up
global.fetch = jest.fn();

describe("displayTool()", () => {
    beforeEach(() => {
        jest.clearAllMocks();

        document.body.innerHTML = `
            <ul id="allTools"></ul>
        `;
    });

// Rendering & Basic Display Tests:
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
            {}
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


    // Edge-Case Input Tests

    test("DisplayTool test: Tool without ID", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            {
                name: "Tool",
                url: "https://example.com",
                tags: ["Dev"]
                // id missing
            }
        ];

        isToolInFavorite.mockResolvedValue(false);

        await displayTools(mockTools, mockList);

        expect(mockList.children.length).toBe(1);

        const firstTool = mockList.children[0];
        expect(firstTool.querySelector(".tool-name").textContent).toBe("Tool");
        expect(firstTool.querySelector(".tool-url").textContent).toBe("https://example.com");
        expect(firstTool.querySelectorAll(".tag").length).toBe(1);

        // Should still render an unfilled star
        expect(firstTool.querySelector(".star").textContent).toBe("☆");
    });

    test("DisplayTool test: Tool without URL or invalid URL", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            {
                id: 99,
                name: "NoLinkTool",
                url: null,              // invalid URL
                tags: ["Test"]
            }
        ];

        isToolInFavorite.mockResolvedValue(false);

        await displayTools(mockTools, mockList);

        expect(mockList.children.length).toBe(1);

        const firstTool = mockList.children[0];

        expect(firstTool.querySelector(".tool-name").textContent).toBe("NoLinkTool");
        expect(firstTool.querySelector(".tool-url").textContent).toBe(""); // EXPECTED EMPTY
        expect(firstTool.querySelectorAll(".tag").length).toBe(1);
        expect(firstTool.querySelector(".star").textContent).toBe("☆");
    });

    test("DisplayTool test: Tool with null or undefined tags", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            {
                id: 1,
                name: "NullTagsTool",
                url: "https://example.com",
                tags: null
            }
        ];

        isToolInFavorite.mockResolvedValue(false);

        await displayTools(mockTools, mockList);

        expect(mockList.children.length).toBe(1);

        const firstTool = mockList.children[0];

        expect(firstTool.querySelector(".tool-name").textContent).toBe("NullTagsTool");
        expect(firstTool.querySelector(".tool-url").textContent).toBe("https://example.com");

        // No tags should be rendered
        expect(firstTool.querySelectorAll(".tag").length).toBe(0);
    });



    // Favorite Toggle Behavior

    // Waits for the next event loop tick so that async operations (like click handlers with fetch)
    // have a chance to complete before assertions
    // we call it below here:
    const flushPromises = () => new Promise(resolve => setTimeout(resolve, 0));

    test("DisplayTool test: Change favorite", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            {
                id: 1,
                name: "Tintin",
                url: "https://google.com",
                tags: ["Dev", "Stage"]
            }
        ];

        // Initially, tool is NOT a favorite
        isToolInFavorite.mockResolvedValue(false);

        // When starClicked runs and calls getCurrentEmployee,
        // we return a fake employee
        getCurrentEmployee.mockResolvedValue({initials: "HOHO"});

        // Fetch call made by starClicked should "succeed"
        fetch.mockResolvedValue({ok: true});

        await displayTools(mockTools, mockList);

        expect(mockList.children.length).toBe(1);

        const firstTool = mockList.children[0];
        const starSpan = firstTool.querySelector(".star");
        const starBtn = firstTool.querySelector(".star-button");

        // Starts unfilled
        expect(starSpan.textContent).toBe("☆");

        // Simulate click
        starBtn.click();

        // Wait for the async click handler to run:
        // - await getCurrentEmployee()
        // - toggle star
        // - await fetch()
        await flushPromises();

        // Now the star should be filled
        expect(starSpan.textContent).toBe("★");
    });

    test("DisplayTool test: Spam change favorite", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            {
                id: 1,
                name: "Tintin",
                url: "https://google.com",
                tags: ["Dev", "Stage"]
            }
        ];

        // Initially, tool is NOT a favorite
        isToolInFavorite.mockResolvedValue(false);

        // When starClicked runs and calls getCurrentEmployee,
        // we return a fake employee
        getCurrentEmployee.mockResolvedValue({initials: "HOHO"});

        // Fetch call made by starClicked should "succeed"
        fetch.mockResolvedValue({ok: true});

        await displayTools(mockTools, mockList);

        expect(mockList.children.length).toBe(1);

        const firstTool = mockList.children[0];
        const starSpan = firstTool.querySelector(".star");
        const starBtn = firstTool.querySelector(".star-button");

        // Starts unfilled
        expect(starSpan.textContent).toBe("☆");

        // Simulate click
        starBtn.click();
        starBtn.click();
        starBtn.click();
        starBtn.click();
        starBtn.click();

        // Wait for the async click handler to run:
        // - await getCurrentEmployee()
        // - toggle star
        // - await fetch()
        await flushPromises();

        // Now the star should be filled
        expect(starSpan.textContent).toBe("★");
    });

    // Test checks that if you try to add a tool to favorites but the fetch fails it should revert the star back to notfilled.
    test("DisplayTool test: Favorite toggle fails", async () => {
        const mockList = document.getElementById("allTools");
        const mockTools = [
            {
                id: 1,
                name: "Tintin",
                url: "https://google.com",
                tags: []
            }
        ];
        isToolInFavorite.mockResolvedValue(false);
        getCurrentEmployee.mockResolvedValue({initials: "HOHO"});

        // Simulate failed fetch
        fetch.mockResolvedValue({ok: false, status: 500});

        await displayTools(mockTools, mockList);

        const starSpan = mockList.querySelector(".star");
        const starBtn = mockList.querySelector(".star-button");

        starBtn.click();
        await flushPromises();

        // Star should revert back
        expect(starSpan.textContent).toBe("☆");
    });

    test("DisplayTool test: displayFavorites is called after successful favorite change", async () => {
        const mockList = document.getElementById("allTools");

        const mockTools = [
            {
                id: 1,
                name: "Tintin",
                url: "https://google.com",
                tags: ["Dev"]
            }
        ];

        isToolInFavorite.mockResolvedValue(false);
        getCurrentEmployee.mockResolvedValue({initials: "HOHO"});

        fetch.mockResolvedValue({ok: true});

        await displayTools(mockTools, mockList);

        const firstTool = mockList.children[0];
        const btn = firstTool.querySelector(".star-button");

        btn.click();
        await flushPromises();

        expect(displayFavorites).toHaveBeenCalledTimes(1);
    });
});