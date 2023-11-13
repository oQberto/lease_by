const fullUrl = window.location.href;
const parts = fullUrl.split('/');
const cityName = parts[parts.length - 1];

ymaps.ready(init);

function init() {
    var myMap = new ymaps.Map("map", {
        center: [53.932193, 27.434136],
        zoom: 14,
        controls: ['trafficControl']
    });

    var clusterer = new ymaps.Clusterer({
        clusterDisableClickZoom: true,
        clusterOpenBalloonOnClick: true,
        preset: 'islands#invertedBlueClusterIcons',
    });

    $.ajax({
        url: '/api/v1/rentals/geocode/' + cityName,
        type: 'GET',
        dataType: 'json',
        success: function (data) {

            data.forEach(function (point) {
                var placemark = new ymaps.Placemark([point.latitude, point.longitude], {balloonContent: point.pointName});

                placemark.events.add('click', function (e) {
                    $('#markerModal').modal('show');
                    $('#markerModalTitle').text(point.pointName);
                });

                clusterer.add(placemark);
            });

            myMap.geoObjects.add(clusterer);
        },
        error: function (error) {
            console.log('Ошибка при получении данных геокодирования:', error);
        }
    });
}
