// <input>�� �־�����
// �� input�� ��������� true
// �� input�� ���ڰ� ������ false
function isEmpty(field) {
	return (!field.value) ? true : false;
}

// <input>, �ּ� ���� ���� �־�����
// �� input�� �� ������ �� ���ڼ� ���� ������ true
// �� input�� �� ������ �� ���ڼ� ���� ������ false
function lessThan(field, charCount) {
	return (field.value.length < charCount);
}

// <input>�� �־�����
// �� input�� �ѱ� ������ true
// �� input�� �ѱ� ������ false
function containsHangul(field) {
	alert("dd");
	var t = "1234567890qwertyuiopasdfghjklzxcvbnm";
	for (var i = 0; i < field.value.length; i++) {
		if (t.indexOf(field.value.toLowerCase()[i]) == -1) {
			return true;
		}
	}
	return false;
}

// <input>, ���ڿ� ��Ʈ ������
// �� input�� ���ڿ��� �ִ� ���ڰ� ������ true
// �� input�� ���ڿ��� �ִ� ���ڰ� ������ false
function notContains(field, charSet){
	for (var i = 0; i < charSet.length; i++) {
		if( field.value.indexOf(charSet[i]) != -1){
			return false;
		}
	}	
	return true;
}

// <input> �� �� �־�����
// �� �� �� �� �ٸ��� true
// �� �� �� �� ������ false
function notEquals(field1, field2){
	return (field1.value != field2.value);
}

// <input> �־��� ��
// �ű⿡ ���� �ƴѰ� ������ true
// �ű⿡ ���ڸ� ������ false
function isNotNumber(field){
	return isNaN(field.value);
}

// <input type="file">, Ȯ���� �־��� ��
// �� ������ �ƴϸ� true
// �� ������ ������ false
function isNotType(field, type){
	// field.value // ���ε� �Ϸ��� ���ϸ� xxx.PNG
	return (field.value.toLowerCase().indexOf(type) == -1);
}
































