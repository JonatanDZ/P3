import { submitForm, formToJSON } from '../../../main/resources/static/js/submitForm.js';
import { MakeToolJSON } from "../../../main/resources/static/js/fetchTool.js";

global.fetch = jest.fn();


describe('submitForm', () => {
    let mockResponse;
    beforeEach(() => {
        jest.clearAllMocks();

        document.body.innerHTML = `
        <div id="addToolDiv" style="display:none;"></div>
        <button class="toggleBtn"></button>
        <input id="toolName" value="Test Tool"/>
        <input id="toolURL" value="http://example.com"/>
        <input id="tags" value="tag1, tag2"/>
        <input type="checkbox" id="isDynamic" checked/>
        <div id="departmentsInput"></div>
        <input type="checkbox" class="departmentsChecks" value="DEPT1" checked>
        <div id="stagesInput"></div>
        <input type="checkbox" class="stagesChecks" value="STAGE1" checked>;
        <div id="jurisdictionsInput"></div>
        <input type="checkbox" class="jurisdictionsChecks" value="DK" checked>
        <input type="checkbox" class="jurisdictionsChecks" value="UK">
        <button id="submitBtn"></button>
    `;
    });
    test('submit form', async () => {


        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        }

        global.fetch.mockResolvedValue(mockResponse);
        await submitForm();

        expect(global.fetch).toHaveBeenCalled();

        const [url, options] = global.fetch.mock.calls[0]; // first call
        console.log("Options:", options);

        const body = JSON.parse(options.body);
        console.log(body.name)
        expect(body.name).toBe('Test Tool');
        expect(body.url).toBe('http://example.com');
        expect(body.tags).toEqual(['tag1', 'tag2']);
        expect(body.dynamic).toBe(true);
        expect(body.departments).toEqual(['DEPT1']);
        expect(body.jurisdictions).toEqual(['DK']);
        expect(body.stages).toEqual(['STAGE1']);



    })
});