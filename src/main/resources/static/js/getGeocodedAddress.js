const houseNo = document.getElementById('houseNo').textContent;
const streetName = document.getElementById('streetName').textContent;
const cityName = document.getElementById('cityName').textContent;

ymaps.ready(init);

function init() {
    $.ajax({
        url: '/api/v1/rentals/geocode/rental-address/' + streetName + ', ' + cityName + ' ' + houseNo,
        type: 'GET',
        dataType: 'json',
        success: function (rentalCentre) {
            var myMap = new ymaps.Map("map", {
                center: [rentalCentre.latitude, rentalCentre.longitude],
                zoom: 14,
                controls: ['trafficControl']
            });

            var clusterer = new ymaps.Clusterer({
                clusterDisableClickZoom: true,
                clusterOpenBalloonOnClick: true,
                preset: 'islands#invertedBlueClusterIcons',
            });

            $.ajax({
                url: '/api/v1/rentals/geocode/rental-address/' + streetName + ', ' + cityName + ' ' + houseNo,
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    var placemark = new ymaps.Placemark([data.latitude, data.longitude], {balloonContent: data.pointName});

                    placemark.events.add('click', function (e) {
                        $('#markerModal').modal('show');
                        $('#markerModalTitle').text(data.pointName);
                    });

                    clusterer.add(placemark);

                    myMap.geoObjects.add(clusterer);
                },
                error: function (error) {
                    console.log('Ошибка при получении данных геокодирования:', error);
                }
            });
        }
    });
}