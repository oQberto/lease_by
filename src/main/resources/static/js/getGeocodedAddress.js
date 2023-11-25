const houseNo = document.getElementById('houseNo');
const streetName = document.getElementById('streetName');
const cityName = document.getElementById('cityName');

ymaps.ready(init);

function init() {
    $.ajax({
        url: '/api/v1/rentals/geocode/city-centre/' + cityName,
        type: 'GET',
        dataType: 'json',
        success: function (cityCentre) {
            var myMap = new ymaps.Map("map", {
                center: [cityCentre.latitude, cityCentre.longitude],
                zoom: 12,
                controls: ['trafficControl']
            });

            var clusterer = new ymaps.Clusterer({
                clusterDisableClickZoom: true,
                clusterOpenBalloonOnClick: true,
                preset: 'islands#invertedBlueClusterIcons',
            });

            $.ajax({
                url: '/api/v1/rentals/geocode/' + cityName + '/' + streetName + '/' + houseNo,
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    var placemark = new ymaps.Placemark([data.latitude, data.longitude], {balloonContent: data.pointName});

                    placemark.events.add('click', function (e) {
                        $('#markerModal').modal('show');
                        $('#markerModalTitle').text(point.pointName);
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