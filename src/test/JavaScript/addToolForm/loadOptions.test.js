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

        global.fetch.mockResolvedValue(mockResponse); //No matter what: receive the body above (mockResponse)

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

    test('Create checkboxes for each item in response', async () => {

        // Setup mock fetch response
        mockResponse = {
            ok: true,
            json: async () => ([ //The body that is received
                {name: 'DK'},
                {name: 'UK'},
                {name: 'US'}
            ])
        }

        global.fetch.mockResolvedValue(mockResponse); //No matter what: receive the body above (mockResponse)

        loadOptions('jurisdictions');

        await new Promise(process.nextTick); //waits until loadOptions('departments'); is done

        // checking if the checkboxes are made correctly
        //DK
        expect(document.querySelector("#DKInput").value).toBe("DK");
        expect(document.querySelector("#DKInput").type).toBe("checkbox");
        expect(document.querySelector("#DKInput").className).toBe("jurisdictionsChecks");
        //UK
        expect(document.querySelector("#UKInput").value).toBe("UK");
        expect(document.querySelector("#UKInput").type).toBe("checkbox");
        expect(document.querySelector("#UKInput").className).toBe("jurisdictionsChecks");
        //US
        expect(document.querySelector("#USInput").value).toBe("US");
        expect(document.querySelector("#USInput").type).toBe("checkbox");
        expect(document.querySelector("#USInput").className).toBe("jurisdictionsChecks");
    });

    test('Handle empty response data', async () => {

        // Setup mock fetch response
        mockResponse = {
            ok: true,
            json: async () => ([ //The body that is received
            ])
        }

        global.fetch.mockResolvedValue(mockResponse);

        loadOptions('jurisdictions');

        await new Promise(process.nextTick);

        expect(document.querySelector("#jurisdictionsInput").innerHTML).toBe("");
    });

    test('Handle different different cases', async () => {

        // Setup mock fetch response
        mockResponse = {
            ok: true,
            json: async () => ([ //The body that is received
                {name: 'lowercase'},
                {name: 'MiXeDcAsE'},
                {name: 'UPPERCASE'}
            ])
        }

        global.fetch.mockResolvedValue(mockResponse); //No matter what: receive the body above (mockResponse)

        loadOptions('departments');

        await new Promise(process.nextTick); //waits until loadOptions('departments'); is done

        // checking if the checkboxes are made correctly
        //lowercase
        expect(document.querySelector("#lowercaseInput").value).toBe("lowercase".toUpperCase());
        expect(document.querySelector("#lowercaseInput").type).toBe("checkbox");
        expect(document.querySelector("#lowercaseInput").className).toBe("departmentsChecks");
        //MiXeDcAsE
        expect(document.querySelector("#MiXeDcAsEInput").value).toBe("MiXeDcAsE".toUpperCase());
        expect(document.querySelector("#MiXeDcAsEInput").type).toBe("checkbox");
        expect(document.querySelector("#MiXeDcAsEInput").className).toBe("departmentsChecks");
        //UPPERCASE
        expect(document.querySelector("#UPPERCASEInput").value).toBe("UPPERCASE".toUpperCase());
        expect(document.querySelector("#UPPERCASEInput").type).toBe("checkbox");
        expect(document.querySelector("#UPPERCASEInput").className).toBe("departmentsChecks");
    });
});