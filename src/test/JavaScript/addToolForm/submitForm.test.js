import { submitForm, formToJSON } from '../../../main/resources/static/js/submitForm.js';
import { poster } from "../../../main/resources/static/js/fetchTool.js";

global.fetch = jest.fn();


describe('submitForm', () => {
    let mockResponse;
    beforeEach(() => {
        jest.clearAllMocks();
//Data used for the tests (simply hard coded HTML)
        document.body.innerHTML = `
        <div id="addToolDiv" style="display:none;"></div>
        <button class="toggleBtn"></button>
        <input id="toolName" value="Test Tool"/>
        <input id="toolURL1" value="http://example."/>
        <b id="toolUser">USER</b>
        <input id="toolURL2" value=".com"/>
        <input id="tags" value="tag1, tag2"/>
        <input type="checkbox" id="isDynamic"/>
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
    test('submit form non dynamic link', async () => {


        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        }
//Uses submitForm on the hard coded HTML to make it "visible" for jest
        global.fetch.mockResolvedValue(mockResponse);
        await submitForm();

        expect(global.fetch).toHaveBeenCalled();

        const [url, options] = global.fetch.mock.calls[0]; // first call

        const body = JSON.parse(options.body);

        expect(body.name).toBe('Test Tool');
        expect(body.url).toBe('http://example.');
        expect(body.tags).toEqual(['tag1', 'tag2']);
        expect(body.dynamic).toBe(false);
        expect(body.departments).toEqual(['DEPT1']);
        expect(body.jurisdictions).toEqual(['DK']);
        expect(body.stages).toEqual(['STAGE1']);
    })
    test('submit form dynamic link', async () => {


        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        }
        document.querySelector("#isDynamic").checked = true;

        global.fetch.mockResolvedValue(mockResponse);
        await submitForm();

        expect(global.fetch).toHaveBeenCalled();

        const [url, options] = global.fetch.mock.calls[0]; // first call

        const body = JSON.parse(options.body);

        expect(body.name).toBe('Test Tool');
        expect(body.url).toBe('http://example.USER.com');
        expect(body.tags).toEqual(['tag1', 'tag2']);
        expect(body.dynamic).toBe(true);
        expect(body.departments).toEqual(['DEPT1']);
        expect(body.jurisdictions).toEqual(['DK']);
        expect(body.stages).toEqual(['STAGE1']);
    })
//Test to see if the tags text is correctly "translated" to the JSON - LAV NY TEST MED TAGS
    test('tags are trimmed and split correctly', () => {
        document.querySelector('#tags').value = '  tagA , tagB,tagC;..  ,  ';
        submitForm();

        const [url, options] = global.fetch.mock.calls[0]; // first call

        const body = JSON.parse(options.body);

        expect(body.tags).toEqual(['tagA', 'tagB', 'tagC;..']);
    });
});