// Toggle eye icon visibility based on input
function toggleEyeIcon(input) {
    const eyeIcon = input.nextElementSibling;
    eyeIcon.style.display = input.value.length > 0 ? 'block' : 'none';
}

document.addEventListener('DOMContentLoaded', function () {
    // Initialize eye icons for any pre-filled values
    document.querySelectorAll('.password-field').forEach(input => {
        toggleEyeIcon(input);
    });

    // Add click handlers for all eye icons
    document.querySelectorAll('.toggle-password').forEach(icon => {
        icon.addEventListener('click', function () {
            const input = this.previousElementSibling;
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            this.classList.toggle('fa-eye');
            this.classList.toggle('fa-eye-slash');
        });
    });
});

window.addEventListener("scroll", function () {
    const navbar = document.getElementById("navbar");
    if (window.scrollY > 50) {
        navbar.classList.add("scrolled");
    } else {
        navbar.classList.remove("scrolled");
    }
});

// Auto-dismiss alerts after 5 seconds
const alerts = document.querySelectorAll('.alert');
alerts.forEach(alert => {
    setTimeout(() => {
        const closeButton = alert.querySelector('.btn-close');
        if (closeButton) {
            closeButton.click();
        }
    }, 5000);
});

function openDeleteModal(button) {
    const userId = button.getAttribute("data-user-id");
    const userName = button.getAttribute("data-user-name");

    // Set text in modal
    document.getElementById("deleteUserId").textContent = userId;
    document.getElementById("deleteUserName").textContent = userName;

    // Set form input value
    document.getElementById("delete-user-id").value = userId;

    // Set form action dynamically
    const form = document.getElementById("deleteUserForm");
    form.action = `/admin/users/delete/${userId}`;
}

document.addEventListener('DOMContentLoaded', function () {
    // Set up delete modal
    const deleteButtons = document.querySelectorAll('.delete-room-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            const roomId = this.getAttribute('data-room-id');
            const roomNumber = this.getAttribute('data-room-number');

            document.getElementById('roomIdToDelete').value = roomId;
            document.getElementById('deleteRoomNumber').textContent = roomNumber;
        });
    });

    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            const closeButton = alert.querySelector('.btn-close');
            if (closeButton) {
                closeButton.click();
            }
        }, 5000);
    });
});

document.querySelectorAll(".delete-hotel-btn").forEach(button => {
    button.addEventListener("click", () => {
        const hotelId = button.getAttribute("data-hotel-id");
        document.getElementById("hotelIdToDelete").value = hotelId;
    });
});



document.addEventListener('DOMContentLoaded', function () {
    // Set up delete modal
    const deleteButtons = document.querySelectorAll('.delete-hotel-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            const hotelId = this.getAttribute('data-hotel-id');
            const hotelName = this.getAttribute('data-hotel-name');

            document.getElementById('hotelIdToDelete').value = hotelId;
            document.getElementById('deleteHotelName').textContent = hotelName;
        });
    });
});

// Store the current hotel details
let currentHotel = {
    name: '',
    price: 0
};

function showReceipt(element) {
    // Get the hotel details from data attributes
    const hotelName = element.getAttribute('data-hotel-name');
    const hotelPrice = parseFloat(element.getAttribute('data-hotel-price'));

    // Set the current hotel details
    currentHotel.name = hotelName;
    currentHotel.price = hotelPrice;

    // Update the modal display
    document.getElementById("modalHotelName").textContent = hotelName;
    document.getElementById("hotelName").value = hotelName;
    document.getElementById("hotelPrice").value = hotelPrice;

    // Reset form fields
    document.getElementById("adultsCount").value = 1;
    document.getElementById("childrenCount").value = 0;
    document.getElementById("checkIn").value = '';
    document.getElementById("checkOut").value = '';
    document.getElementById('totalAmount').textContent = '0';
}

function updateTotal() {
    // Get all required values
    const checkIn = document.getElementById('checkIn').value;
    const checkOut = document.getElementById('checkOut').value;

    // Only calculate if both dates are selected
    if (!checkIn || !checkOut) {
        document.getElementById('totalAmount').textContent = '0';
        return;
    }

    // Parse dates
    const checkInDate = new Date(checkIn);
    const checkOutDate = new Date(checkOut);

    // Calculate night difference
    const timeDiff = checkOutDate - checkInDate;
    const totalNights = Math.ceil(timeDiff / (1000 * 3600 * 24));

    // Validate dates
    if (totalNights <= 0) {
        alert("Check-out date must be after check-in date.");
        document.getElementById('totalAmount').textContent = '0';
        return;
    }

    // Calculate total amount
    const totalAmount = totalNights * currentHotel.price;

    // Update the display
    document.getElementById('totalAmount').textContent = totalAmount;
}

// Add event listeners after DOM loads
document.addEventListener('DOMContentLoaded', function () {
    // Ensure updateTotal runs on any relevant change
    document.getElementById('checkIn').addEventListener('change', updateTotal);
    document.getElementById('checkOut').addEventListener('change', updateTotal);
    document.getElementById('adultsCount').addEventListener('change', updateTotal);
    document.getElementById('childrenCount').addEventListener('change', updateTotal);
});
