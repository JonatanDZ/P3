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


describe('toggleForm', () => {
    test('shows the form when hidden', () => {
        const addToolDiv = document.querySelector('#addToolDiv');
        let formIsShown = false;

        formIsShown = toggleForm(formIsShown);
        expect(addToolDiv.style.display).toBe('block');
        expect(formIsShown).toBe(true);
    });
    test('hides the form when shown', () => {
        const addToolDiv = document.querySelector('#addToolDiv');
        let formIsShown = true;

        formIsShown = toggleForm(formIsShown);
        expect(addToolDiv.style.display).toBe('none');
        expect(formIsShown).toBe(false);
    });
});