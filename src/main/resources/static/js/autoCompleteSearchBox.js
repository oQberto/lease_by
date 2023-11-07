const searchWrapper = document.querySelector(".search-input");
const inputBox = searchWrapper.querySelector("input");
const suggBox = searchWrapper.querySelector(".autocom-box");

inputBox.onkeyup = (e)=>{
    let userData = e.target.value;

    if (userData) {
        suggBox.innerHTML = '';
        searchWrapper.classList.add("active");

        fetch(`/api/v1/rentals/${userData}`)
            .then(response => response.json())
            .then(data => {
                if (data && data.length > 0) {
                    const addresses = data.map(item => item);

                    showSuggestions(addresses);
                }
            })
            .catch(error => console.error(error));
    } else {
        searchWrapper.classList.remove("active");
    }
}

function showSuggestions(list) {
    suggBox.innerHTML = list.map(address => `<li>${address}</li>`).join('');
}

suggBox.addEventListener("click", function(e) {
    if (e.target && e.target.nodeName === "LI") {
        inputBox.value = e.target.textContent;
        searchWrapper.classList.remove("active");
    }
});

inputBox.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') {
        e.preventDefault();
        const userData = inputBox.value.trim();
        if (userData) {
            window.location.href = `/rentals/address/${encodeURIComponent(userData)}`;
        }
    }
});