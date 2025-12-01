import {footerUpdate} from '../../../main/resources/static/js/footerVariable.js';

describe("footerUpdate()", () => {
    beforeEach(() => {
        document.body.innerHTML = `
            <div class="branchSelector">
            <input type="radio" id="dev" name="branch" value="DEVELOPMENT" checked>
            <label for="dev" style="background-color: rgb(255, 0, 0)">Dev</label><br>

            <input type="radio" id="stage" name="branch" value="STAGING">
            <label for="stage" style="background-color: rgb(0, 255, 0)">Stage</label><br>

            <input type="radio" id="prod" name="branch" value="PRODUCTION">
            <label for="prod" style="background-color: rgb(0, 0, 255)">Prod</label><br>
        </div>
        <footer></footer>
        `;
    });
    test("sets footer background after what stage is selected", () => {
        const footer = document.querySelector("footer");

        // Is default checked as DEV
        footerUpdate();

        expect(footer.textContent).toBe("Dev");
        expect(footer.style.backgroundColor).toBe("rgb(255, 0, 0)")

        // Switching to stage
        const stageInput = document.getElementById("stage");
        stageInput.checked = true;
        footerUpdate();

        expect(footer.textContent).toBe("Stage");
        expect(footer.style.backgroundColor).toBe("rgb(0, 255, 0)")


        // Switching to prod
        const prodInput = document.getElementById("prod");
        prodInput.checked = true;
        footerUpdate();

        expect(footer.textContent).toBe("Prod");
        expect(footer.style.backgroundColor).toBe("rgb(0, 0, 255)")
    });

    test("No stage selected", () => {
        document.querySelectorAll("input[name='branch']").forEach(element => (element.checked = false));

        const footer = document.querySelector("footer");
        footerUpdate();
        expect(footer.textContent).toBe("");
    });
});