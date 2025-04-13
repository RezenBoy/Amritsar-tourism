// Toggle eye icon visibility based on input
function toggleEyeIcon(input) {
    const eyeIcon = input.nextElementSibling;
    eyeIcon.style.display = input.value.length > 0 ? 'block' : 'none';
}

document.addEventListener('DOMContentLoaded', function() {
    // Initialize eye icons for any pre-filled values
    document.querySelectorAll('.password-field').forEach(input => {
        toggleEyeIcon(input);
    });

    // Add click handlers for all eye icons
    document.querySelectorAll('.toggle-password').forEach(icon => {
        icon.addEventListener('click', function() {
            const input = this.previousElementSibling;
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            this.classList.toggle('fa-eye');
            this.classList.toggle('fa-eye-slash');
        });
    });
});