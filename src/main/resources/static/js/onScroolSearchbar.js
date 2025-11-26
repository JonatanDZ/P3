document.addEventListener("DOMContentLoaded", () => {
    const fixed_el = document.querySelector('#searchbar');
    const fixed_el2 = document.querySelector(".searchbar-items");
    window.addEventListener("scroll", () => {
        const y = window.scrollY;

        if (y >= 100) {
            fixed_el.classList.add('fixed');
            fixed_el2.classList.add('fixed');

        } else {
            fixed_el.classList.remove('fixed');
            fixed_el2.classList.remove('fixed');
        }
    });
});