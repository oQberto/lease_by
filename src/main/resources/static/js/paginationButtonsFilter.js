function submitForm(direction) {
    var form;
    var filters = localStorage.getItem("filters");

    if (direction === 'prev') {
        form = document.getElementById('prevForm');
    } else if (direction === 'next') {
        form = document.getElementById('nextForm');
    }

    if (form) {

        filters = JSON.parse(filters);

        if (!filters) {
            filters = {};
        }

        if (!filters.cityName) {
            filters.cityName = new URL(window.location.href).pathname.split('/').pop();
        }

        for (var key in filters) {
            if (filters.hasOwnProperty(key)) {
                var input = document.createElement('input');
                input.type = 'hidden';
                input.name = key;
                input.value = filters[key];
                form.appendChild(input);
            }
        }

        form.submit();
    }
}