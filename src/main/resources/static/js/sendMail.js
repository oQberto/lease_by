function sendEmail() {
    var form = document.getElementById('emailForm');
    var formData = new FormData(form);

    $.ajax({
        type: 'POST',
        url: form.action,
        data: formData,
        success: function(response) {
            openPopup();
        },
        error: function(error) {
            alert('Error sending email: ' + error.responseText);
        },
        cache: false,
        contentType: false,
        processData: false
    });
}

function openPopup() {
    document.getElementById('overlay').style.display = 'block';
    document.getElementById('popup').style.display = 'block';
}

function closePopup() {
    document.getElementById('overlay').style.display = 'none';
    document.getElementById('popup').style.display = 'none';
}