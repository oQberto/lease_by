document.addEventListener('DOMContentLoaded', function () {

    var form = document.querySelector('.filterForm');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        var filters = {};

        for (var i = 0; i < form.elements.length; i++) {
            var element = form.elements[i];

            if (element.type !== 'submit' && element.name) {
                if (element.type === 'checkbox') {
                    if (element.checked) {
                        if (!filters[element.name]) {
                            filters[element.name] = [];
                        }
                        filters[element.name].push(element.value);
                    }
                } else if (element.type === 'radio') {
                    if (element.checked) {
                        filters[element.name] = element.value;
                    }
                } else {
                    filters[element.name] = element.value;
                }
            }
        }

        localStorage.setItem('filters', JSON.stringify(filters));

        form.submit();
    });

    var savedFilters = localStorage.getItem('filters');
    if (savedFilters) {

        var filters = JSON.parse(savedFilters);

        for (var key in filters) {
            if (filters.hasOwnProperty(key)) {
                var element = form.elements[key];
                if (element) {
                    if (Array.isArray(filters[key])) {
                        filters[key].forEach(function (value) {
                            var checkbox = document.getElementById(value);
                            if (checkbox) {
                                checkbox.checked = true;
                            }
                        });
                    } else {
                        var checkbox = document.getElementById(filters[key]);
                        if (checkbox) {
                            checkbox.checked = true;
                        }
                    }
                }
            }
        }
    }
});