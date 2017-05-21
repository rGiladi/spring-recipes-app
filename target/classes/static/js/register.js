var username = document.getElementById('username');
username.addEventListener('keyup', function() {
	if (this.value.trim() != this.value) {
		toast('שם המשתמש לא יכול להתחיל או להסתיים ברווח');
		this.value = this.value.trim();
	}
	
	if (this.value.length > 24) {
		this.value = this.value.substr(0, 24);
		toast('שם המשתמש צריך להכיל בין 6 ל 24 תווים');
	}
});

var password = document.getElementById('password');
password.addEventListener('keyup', function() {
	if ( this.value.length > 32 ) {
		this.value = this.value.substr(0, 32);
		toast('הסיסמא צריכה להכיל בין 8 ל 32 תווים')
	}
});