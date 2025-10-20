import {loadOptions} from "../../../main/resources/static/js/loadOptions.js";

global.fetch = jest.fn(); //This makes the fetch in the function be the mockdata we have selected
//document.querySelector = jest.fn();

describe('loadOptions', () => {
    let mockResponse;

    beforeEach(() => {
        // Reset all mocks
        jest.clearAllMocks();

        // Setup mock fetch response
        mockResponse = {
            json: jest.fn()
        };



        global.fetch.mockResolvedValue(mockResponse);

        document.body.innerHTML = `
        <div id="addToolDiv" style="display:none;"></div>
        <button class="toggleBtn"></button>
        <input id="toolName" value="Test Tool"/>
        <input id="toolURL" value="http://example.com"/>
        <input id="tags" value="tag1, tag2"/>
        <input type="checkbox" id="isDynamic" checked/>
        <div id="departmentsInput"></div>
        <div id="jurisdictionsInput"></div>
        <button id="submitBtn"></button>
        `;
    });

    test('should create checkboxes for each item in the response', async () => {
        const testData = [
            {name: 'HR'},
            {name: 'Legal'},
            {name: 'Players'}
        ];
        mockResponse.json.mockResolvedValue(testData);

        loadOptions('departments');

        await new Promise(process.nextTick);

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

        // Should create 3 checkBoxDiv elements (one for each item)
        //expect(mockDropdown.appendChild).toHaveBeenCalledTimes(3);
    });


});