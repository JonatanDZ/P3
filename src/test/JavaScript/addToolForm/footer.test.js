import {footerUpdate} from '../../../main/resources/static/js/footerVariable.js';

global.fetch = jest.fn(); //This prevents the real API call

describe("footerUpdate", () => {
    let mockResponse;

    beforeEach(() => {
        jest.clearAllMocks();

        document.body.innerHTML = `
        <div className="branchSelector">
            <input type="radio" id="dev" name="branch" value="DEVELOPMENT" checked/>
            <label htmlFor="dev">Dev</label><br/>

            <input type="radio" id="stage" name="branch" value="STAGING"/>
            <label htmlFor="stage">Stage</label><br/>

            <input type="radio" id="prod" name="branch" value="PRODUCTION"/>
            <label htmlFor="prod">Prod</label><br/>
        </div> 
        `;

        test("Check", async () => {
        mockResponse = {
            ok: true,
            json: async () =>([
                {name: "DEVELOPMENT"},
                {name: "STAGING"},
                {name: "PRODUCTION"},
            ])
        };

        global.fetch(mockResolvedValue(mockResponse));

        loadOptions("DEVELOPMENT");

        await new Promise(process.nextTick);

        expect(document.querySelector("#dev").value).toBe("DEVELOPMENT");


        });
    });
});
