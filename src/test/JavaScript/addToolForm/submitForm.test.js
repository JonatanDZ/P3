import { submitForm, formToJSON } from '../../../main/resources/static/js/submitForm.js';
import { poster } from "../../../main/resources/static/js/fetchTool.js";
import { addTagChip } from "../../../main/resources/static/js/loadOptions.js";

global.fetch = jest.fn();

// Making this mock, to avoid DOM changes
jest.mock('../../../main/resources/static/js/fetchTool.js', () => ({
    poster: jest.fn()
}));

jest.mock('../../../main/resources/static/js/loadOptions.js', () => ({
    addTagChip: jest.fn()
}));



describe('submitForm', () => {
    let mockResponse;
    beforeEach(() => {
        jest.clearAllMocks();
//Data used for the tests (simply hard coded HTML)
        document.body.innerHTML = `
        <div id="addToolDiv" style="display:none;"></div>
        <button class="toggleBtn"></button>
        <input type="checkbox" id="isPersonal">
        <input id="toolName" value="Test Tool"/>
        <input id="toolURL1" value="http://example."/>
        <b id="toolUser">USER</b>
        <input id="toolURL2" value=".com"/>
        <div class="tag-chip" data-tag="6" data-tag-name="Al-Qaeda"><span>Al-Qaeda</span><button type="button">x</button></div>
        <input type="checkbox" id="isDynamic"/>
        <div class="checkBoxDiv"><label>DevOps</label><input type="checkbox" id="DevOpsInput" value="1" class="departmentsChecks" checked></div>
        <div class="checkBoxDiv">
            <label for="stagingInput"> Staging </label>
            <input id="stagingInput" class="stagesChecks" type="checkbox" value="2" name="Staging" checked>
        </div>
        <div class="checkBoxDiv"><label>DK</label><input type="checkbox" id="DKInput" value="1" class="jurisdictionsChecks" checked></div>
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
        expect(body.tags).toEqual([{"id":"6"}]);
        expect(body.is_dynamic).toBe(false);
        expect(body.departments).toEqual([{"id":"1"}]);
        expect(body.jurisdictions).toEqual([{"id":"1"}]);
        expect(body.stages).toEqual([{"id":"2"}]);
    })
    test('submit form dynamic link', async () => {


        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        }
        document.querySelector("#isPersonal").checked = false;
        document.querySelector("#isDynamic").checked = true;

        global.fetch.mockResolvedValue(mockResponse);
        await submitForm();

        expect(global.fetch).toHaveBeenCalled();

        const [url, options] = global.fetch.mock.calls[0]; // first call

        const body = JSON.parse(options.body);

        expect(body.name).toBe('Test Tool');
        expect(body.url).toBe('http://example.USER.com');
        expect(body.is_dynamic).toBe(true);
        expect(body.tags).toEqual([{"id":"6"}]);
        expect(body.departments).toEqual([{"id":"1"}]);
        expect(body.jurisdictions).toEqual([{"id":"1"}]);
        expect(body.stages).toEqual([{"id":"2"}]);
    })
    test('submit form when isPersonal is checked', async () => {

        mockResponse = {
            ok: true,
            json: async () => ({ success: true }),
        };

        document.querySelector("#isPersonal").checked = true;

        global.fetch.mockResolvedValue(mockResponse);
        await submitForm();

        const [url, options] = global.fetch.mock.calls[0];
        const body = JSON.parse(options.body);

        expect(body.is_personal).toBe(true);
    });
});