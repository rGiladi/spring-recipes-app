var username = document.getElementById('username');
username.addEventListener('focusout', function() {
	if (this.value.trim() != this.value) {
		toast('שם המשתמש לא יכול להתחיל או להסתיים ברווח');
		this.value = this.value.trim();
	}
	
	if (this.value.length > 24) {
		this.value = this.value.substr(0, 24);
		toast('שם המשתמש צריך להכיל בין 6 ל 24 תווים');
	}
});