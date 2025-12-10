export function footerUpdate() {
    const checked = document.querySelector('.branchSelector input[name="branch"]:checked');
    if (!checked) return;

    const label = document.querySelector(`label[for="${checked.id}"]`) || checked.nextSibling;
    const styles = label ? getComputedStyle(label) : null;

    //background & foreground
    //if styles exists set background color             styles ? styles.backgroundColor : undefined;
    const bg = styles?.backgroundColor;
    const fg = styles?.color;

    const footerChip = document.querySelector('footer');
    if (!footerChip) return;

    footerChip.textContent = label?.textContent?.trim() || checked.value;
    if(bg) footerChip.style.backgroundColor = bg;
    if(fg) footerChip.style.color = fg;
}

window.addEventListener('DOMContentLoaded', () => {
    footerUpdate();
    document.querySelector('.branchSelector')?.addEventListener('change', (e) => {
        if (e.target.matches('input[name="branch"]')) footerUpdate();
    });
});

