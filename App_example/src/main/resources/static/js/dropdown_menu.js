document.getElementById('user-info').addEventListener('click', function(event) {
    event.stopPropagation();
    const dropdownContent = document.getElementById('dropdown-content');
    dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block';
});

document.addEventListener('click', function() {
    const dropdownContent = document.getElementById('dropdown-content');
    dropdownContent.style.display = 'none';
});