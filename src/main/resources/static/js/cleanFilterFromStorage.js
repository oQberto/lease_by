var cancelButton = document.getElementById('clearButton');

cancelButton.addEventListener('click', function () {

    localStorage.removeItem('filters');

    $.ajax({
        type: 'GET',
        url: '/rentals/' + new URL(window.location.href).pathname.split('/').pop(),
        success: function (data) {
            console.log('Cancel request sent successfully!');
        },
        error: function (error) {
            console.error('Error sending cancel request:', error);
        }
    });
    location.reload();
});