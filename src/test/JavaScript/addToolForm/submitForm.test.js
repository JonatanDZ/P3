import { toggleForm } from '../../../main/resources/static/js/toggleForm.js';



beforeEach(() => {
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

describe('submitForm', () => {
    test('collects form data and calls MakeToolJSON', () => {
        document.body.innerHTML +=
            <input type="checkbox" class="departmentsChecks" value="DEPT1" checked>
                <input type="checkbox" class="jurisdictionsChecks" value="JUR1" checked>
                    <input type="checkbox" class="stagesChecks" value="STAGE1" checked>
                        ;

                        const jsonBody = submitForm();

                        expect(MakeToolJSON).toHaveBeenCalledTimes(1);
                        const arg = JSON.parse(MakeToolJSON.mock.calls[0][0]);
                        expect(arg.name).toBe('Test Tool');
                        expect(arg.url).toBe('http://example.com'/);
                        expect(arg.tags).toEqual(['tag1', 'tag2']);
                        expect(arg.dynamic).toBe(true);
                        expect(arg.departments).toEqual(['DEPT1']);
                        expect(arg.jurisdictions).toEqual(['JUR1']);
                        expect(arg.stages).toEqual(['STAGE1']);
                        });
                        });