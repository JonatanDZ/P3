import {loadOptions} from "../../../main/resources/static/js/loadOptions.js";


// https://jestjs.io/docs/mock-functions
global.fetch = jest.fn(); //This prevents the real API call

describe('loadOptions', () => {
    let mockResponse;

    beforeEach(() => { // cleans the environment before each test
        // Reset all mocks
        jest.clearAllMocks();

        // Mock body
        document.body.innerHTML = `
        <div id="addToolDiv" style="display:none;"></div>
        <button class="toggleBtn"></button>
        <input id="toolName" value="Test Tool"/>
        <input id="toolURL" value="https://example.com"/>
        <input id="tags" value="tag1, tag2"/>
        <input type="checkbox" id="isDynamic" checked/>
        <div id="departmentsInput"></div>
        <div id="jurisdictionsInput"></div>
        <button id="submitBtn"></button>
        `;
    });

    test('Create checkboxes for each item in response', async () => {

        // Setup mock fetch response
        mockResponse = {
            ok: true,
            json: async () => ([ //The body that is received
                {name: 'HR'},
                {name: 'Legal'},
                {name: 'Players'}
            ])
        }

        global.fetch.mockResolvedValue(mockResponse); //No matter what receive the body above

        loadOptions('departments');

        await new Promise(process.nextTick); //waits until loadOptions('departments'); is done

        // checking if the checkboxes are made correctly
        //HR
        expect(document.querySelector("#HRInput").value).toBe("HR");
        expect(document.querySelector("#HRInput").type).toBe("checkbox");
        expect(document.querySelector("#HRInput").className).toBe("departmentsChecks");
        //Legal
        expect(document.querySelector("#LegalInput").value).toBe("LEGAL");
        expect(document.querySelector("#LegalInput").type).toBe("checkbox");
        expect(document.querySelector("#LegalInput").className).toBe("departmentsChecks");
        //Players
        expect(document.querySelector("#PlayersInput").value).toBe("PLAYERS");
        expect(document.querySelector("#PlayersInput").type).toBe("checkbox");
        expect(document.querySelector("#PlayersInput").className).toBe("departmentsChecks");
    });
});