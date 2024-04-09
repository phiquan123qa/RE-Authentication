let map;
let geocoder;
const fullAddress = document.getElementById("fullAddress").textContent;
async function initMap() {
    const { Map } = await google.maps.importLibrary("maps");
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
    const { Geocoder } = await google.maps.importLibrary("geocoding")
    var defaultLocation = { lat: 40.7128, lng: -74.0060 };
    map = new Map(document.getElementById("map"), {
        center: defaultLocation,
        zoom: 15,
        mapId: "4504f8b37365c3d0",
    });
    const marker = new AdvancedMarkerElement({
        map,
        position: defaultLocation,
    });
    geocoder = new Geocoder();
    return Promise.resolve();
}
async function searchByAddress(address) {
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
    await initMap();
    if (!geocoder) {
        console.error("Geocoder not initialized");
        return;
    }
    geocoder.geocode({ address: address }, (results, status) => {
        if (status === "OK") {
            map.setCenter(results[0].geometry.location);
            const marker = new AdvancedMarkerElement({
                map,
                position: results[0].geometry.location,
            });
        } else {
            console.error("Geocode was not successful for the following reason: " + status);
        }
    });
}
searchByAddress(fullAddress).then(v => console.log("found address"));