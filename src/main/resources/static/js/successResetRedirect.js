document.getElementById('reset-form').addEventListener('submit', function(event) {
    event.preventDefault();

    fetch(this.action, {
        method: 'POST',
        body: new FormData(this),
    })
        .then(response => {
            if (response.ok) {
                window.location.href = 'http://localhost:8080/cities';
            } else {
                console.error('Error:', response.status);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
});