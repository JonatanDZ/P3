//import * as test from "node:test";

jest.mock('../../../main/resources/static/js/fetchTool.js', () => ({
    MakeToolJSON: jest.fn(),
}));

import { submitForm, formToJSON } from '../../../main/resources/static/js/submitForm.js';
import { MakeToolJSON } from "../../../main/resources/static/js/fetchTool.js";

// Create a mock reload function
const mockReload = jest.fn();

// Replace the global location object entirely BEFORE any test runs
delete global.window.location;
global.window.location = {
    assign: jest.fn(),
    replace: jest.fn(),
    reload: mockReload,
    href: '',
};


global.fetch = jest.fn();

describe('submitForm', () => {
    let mockResponse;

    beforeEach(() => {
        jest.clearAllMocks();
        mockReload.mockClear();

        document.body.innerHTML = `
      <div id="addToolDiv" style="display:none;"></div>
      <button class="toggleBtn"></button>
      <input id="toolName" value="Test Tool"/>
      <input id="toolURL1" value="http://example.com"/>
      <input id="tags" value="tag1, tag2"/>
      <input type="checkbox" id="isDynamic"/>
      <div id="departmentsInput"></div>
      <input type="checkbox" class="departmentsChecks" value="DEPT1" checked>
      <div id="stagesInput"></div>
      <input type="checkbox" class="stagesChecks" value="STAGE1" checked>
      <div id="jurisdictionsInput"></div>
      <input type="checkbox" class="jurisdictionsChecks" value="DK" checked>
      <input type="checkbox" class="jurisdictionsChecks" value="UK">
      <button id="submitBtn"></button>
    `;
    });

    afterEach(() => {
        jest.restoreAllMocks();
    });

    test('submit form', async () => {
        await submitForm();

        expect(MakeToolJSON).toHaveBeenCalledTimes(1);

        const body = JSON.parse(MakeToolJSON.mock.calls[0][0]);
        expect(body.name).toBe('Test Tool');
        expect(body.url).toBe('http://example.com');
        expect(body.tags).toEqual(['tag1', 'tag2']);
        expect(body.dynamic).toBe(false);
        expect(body.departments).toEqual(['DEPT1']);
        expect(body.jurisdictions).toEqual(['DK']);
        expect(body.stages).toEqual(['STAGE1']);
    });

    test('missing name', () => {
        document.querySelector('#toolName').value = '';
        submitForm();

        expect(MakeToolJSON).toHaveBeenCalledTimes(1);
        const body = JSON.parse(MakeToolJSON.mock.calls[0][0]);
        expect(body.name).toEqual('');
        expect(body.url).toEqual('http://example.com');
    });

    test('invalid URL', () => {
        document.querySelector('#toolURL1').value = '';
        submitForm();

        expect(MakeToolJSON).toHaveBeenCalledTimes(1);
        const body = JSON.parse(MakeToolJSON.mock.calls[0][0]);
        expect(body.url).toBe('');
        expect(body.name).toBe('Test Tool');
    });

    test('no checkboxes checked', () => {
        document.body.innerHTML = `
      <input id="toolName" value="NoChecks">
      <input id="toolURL1" value="http://example.com"/>
      <input id="tags" value="tag1, tag2"/>
      <input type="checkbox" id="isDynamic"/>
      <input type="checkbox" id="departmentsChecks" value="DEPT1">
      <input type="checkbox" id="stagesChecks" value="STAGE1">
      <input type="checkbox" id="jurisdictionsChecks" value="DK">
    `;

        submitForm();

        const body = JSON.parse(MakeToolJSON.mock.calls[0][0]);
        expect(body.departments).toEqual([]);
        expect(body.stages).toEqual([]);
        expect(body.jurisdictions).toEqual([]);
        expect(body.dynamic).toEqual(false);
    });

    test('tags are trimmed and split correctly', () => {
        document.querySelector('#tags').value = '  tagA , tagB,tagC  ,  ';
        submitForm();

        const body = JSON.parse(MakeToolJSON.mock.calls[0][0]);
        expect(body.tags).toEqual(['tagA', 'tagB', 'tagC']);
    });
});