function writeReplCheck(writeForm){
	// submit�� ������ form���� isr_txt
	var txtField = writeForm.isr_txt;
	
	if(isEmpty(txtField)){
		alert("���� Ȯ��");
		txtField.focus();
		return false;
	}
	return true;
}
function snsSearchCheck(){
	// �� �������� �̸��� snsSearchForm�� form���� is_txt
	var txtField = document.snsSearchForm.is_txt;
	if (isEmpty(txtField)) {
		alert("�Է� Ȯ��");
		txtField.value = "";
		txtField.focus();
		return false;
	}
	return true;
}
function snsWriteCheck() {
	var txtField = document.snsWriteForm.is_txt;
	if (isEmpty(txtField)) {
		alert("�Է� Ȯ��");
		txtField.value = "";
		txtField.focus();
		return false;
	}
	return true;
} 
/*function updateCheck() {
	var idField = document.updateForm.im_id;
	var pwField = document.updateForm.im_pw;
	var pwChkField = document.updateForm.im_pwChk;
	var nameField = document.updateForm.im_name;
	var addrField = document.updateForm.im_addr;
	var imgField = document.updateForm.im_img;

	if (isEmpty(idField) || containsHangul(idField)) {
		alert("id Ȯ��");
		idField.value = "";
		idField.focus();
		return false;
	} else if (isEmpty(pwField) || notEquals(pwField, pwChkField)
			|| lessThan(pwField, 4) || notContains(pwField, "1234567890")
			|| notContains(pwField, "qwertyuiopasdfghjklzxcvbnm")
			|| notContains(pwField, "QWERTYUIOPASDFGHJKLZXCVBNM")) {
		alert("pw Ȯ��");
		pwField.value = "";
		pwChkField.value = "";
		pwField.focus();
		return false;
	} else if (isEmpty(nameField)) {
		alert("�̸� Ȯ��");
		nameField.value = "";
		nameField.focus();
		return false;
	} else if (isEmpty(addrField)) {
		alert("������ Ȯ��");
		addrField.value = "";
		addrField.focus();
		return false;
	} else if (isEmpty(imgField)) {
		return true;
	} else if (isNotType(imgField, ".png") && isNotType(imgField, ".gif")
			&& isNotType(imgField, ".jpg") && isNotType(imgField, ".bmp")) {
		alert("���� �ȹٷ�");
		imgField.value = "";
		imgField.focus();
		return false;
	}
	return true;
}*/

function updateCheck(docBox) {

	var pwField = document.updateForm.pw;
	var pwChkField = document.updateForm.pwck;
	var nameField = document.updateForm.name;
	var sexField = document.updateForm.sex;
	var imgField = document.updateForm.img;
	
	//confirm(sexField.name+":"+docBox.name);
	if ((pwField.name == docBox.name) && (isEmpty(pwField) || notEquals(pwField, pwChkField)
			|| lessThan(pwField, 4) || notContains(pwField, "1234567890")
			|| notContains(pwField, "qwertyuiopasdfghjklzxcvbnm")
			|| notContains(pwField, "QWERTYUIOPASDFGHJKLZXCVBNM"))) {
/*		alert(isEmpty(pwField));
		alert(notEquals(pwField, pwChkField));
		alert(lessThan(pwField, 4));
		alert(notContains(pwField, "1234567890"));
		alert("pw Ȯ��");*/
		docBox.setCustomValidity('pw Ȯ��!!');
		//pwField.value = "";
		//pwChkField.value = "";
		pwField.focus();
		return false;
	} else if ((nameField.name == docBox.name) && isEmpty(nameField)) {
		//alert("�̸� Ȯ��");
		docBox.setCustomValidity('�̸� Ȯ��!!');
		nameField.value = "";
		nameField.focus();
		return false;
	} else if ((imgField.name == docBox.name) && (isEmpty(imgField)
			|| (isNotType(imgField, ".png") && isNotType(imgField, ".gif")
					&& isNotType(imgField, ".jpg") && isNotType(imgField,
					".bmp")))) {
		//alert("���� �ȹٷ�");
		docBox.setCustomValidity('���� Ȯ��!!');
		imgField.value = "";
		imgField.focus();
		return false;
	}else {
		docBox.setCustomValidity('');
    }
	
	return true;
}

function joinCheck(docBox) {

	var idField = document.joinForm.id;
	var pwField = document.joinForm.pw;
	var pwChkField = document.joinForm.pwck;
//	var nameField = document.joinForm.name;
	var sexField = document.joinForm.sex;
//	var imgField = document.joinForm.img;

	if ((idField.name == docBox.name) && ( isEmpty(idField) || containsHangul(idField))) {

		docBox.setCustomValidity('id Ȯ��!!');
			idField.value = "";
//			idField.focus();
			return false;
		
	} else if ((pwField.name == docBox.name) && (isEmpty(pwField) || notEquals(pwField, pwChkField)
			|| lessThan(pwField, 4) || notContains(pwField, "1234567890")
			|| notContains(pwField, "qwertyuiopasdfghjklzxcvbnm")
			|| notContains(pwField, "QWERTYUIOPASDFGHJKLZXCVBNM"))) {
/*		alert(isEmpty(pwField));
		alert(notEquals(pwField, pwChkField));
		alert(lessThan(pwField, 4));
		alert(notContains(pwField, "1234567890"));
		alert("pw Ȯ��");*/
		docBox.setCustomValidity('pw Ȯ��!!');
		//pwField.value = "";
		//pwChkField.value = "";
//		pwField.focus();
		return false;
	} /*else if ((nameField.name == docBox.name) && isEmpty(nameField)) {
		//alert("�̸� Ȯ��");
		docBox.setCustomValidity('�̸� Ȯ��!!');
		nameField.value = "";
		nameField.focus();
		return false;
	}*/  else if ((sexField.name == docBox.name) && isEmpty(sexField)) {
		//alert("�̸� Ȯ��");
		docBox.setCustomValidity('���� Ȯ��!!');
		sexField.value = "";
//		sexField.focus();
		return false;
	} /*else if ((imgField.name == docBox.name) && (isEmpty(imgField)
			|| (isNotType(imgField, ".png") && isNotType(imgField, ".gif")
					&& isNotType(imgField, ".jpg") && isNotType(imgField,
					".bmp")))) {
		//alert("���� �ȹٷ�");
		docBox.setCustomValidity('���� Ȯ��!!');
		imgField.value = "";
		imgField.focus();
		return false;
	}*/else {
		docBox.setCustomValidity('');
    }
	
	return true;
}


function loginCheck(textbox) {
	var idField = document.loginForm.id;
	var pwField = document.loginForm.pw;

	//alert(12121);
	//alert(idField.name);
	//alert(textbox.name);
	idField.setCustomValidity('���̵� �Է��ϼ���!');
    if ((idField.name == textbox.name) && isEmpty(idField)) {
        textbox.setCustomValidity('���̵� �Է��ϼ���!');
        idField.value = "";
		idField.focus();
		return false;
    }
    else if ((pwField.name == textbox.name) &&isEmpty(pwField)){
        textbox.setCustomValidity('�н����带 �Է��ϼ���.');
        pwField.value = "";
		pwField.focus();
		return false;
    }
    else {
       textbox.setCustomValidity('');
    }
    return true;
}

//<input>�� �־�����
//�� input�� ��������� true
//�� input�� ���ڰ� ������ false
function isEmpty(field) {

	return (!field.value) ? true : false;
}

//<input>, �ּ� ���� ���� �־�����
//�� input�� �� ������ �� ���ڼ� ���� ������ true
//�� input�� �� ������ �� ���ڼ� ���� ������ false
function lessThan(field, charCount) {
	return (field.value.length < charCount);
}

//<input>�� �־�����
//�� input�� �ѱ� ������ true
//�� input�� �ѱ� ������ false
function containsHangul(field) {

	var t = "1234567890qwertyuiopasdfghjklzxcvbnm";
	for (var i = 0; i < field.value.length; i++) {
		if (t.indexOf(field.value.toLowerCase()[i]) == -1) {
			return true;
		}
	}
	return false;
}

//<input>, ���ڿ� ��Ʈ ������
//�� input�� ���ڿ��� �ִ� ���ڰ� ������ true
//�� input�� ���ڿ��� �ִ� ���ڰ� ������ false
function notContains(field, charSet){
	for (var i = 0; i < charSet.length; i++) {
		if( field.value.indexOf(charSet[i]) != -1){
			return false;
		}
	}	
	return true;
}

//<input> �� �� �־�����
//�� �� �� �� �ٸ��� true
//�� �� �� �� ������ false
function notEquals(field1, field2){
	return (field1.value != field2.value);
}

//<input> �־��� ��
//�ű⿡ ���� �ƴѰ� ������ true
//�ű⿡ ���ڸ� ������ false
function isNotNumber(field){
	return isNaN(field.value);
}

//<input type="file">, Ȯ���� �־��� ��
//�� ������ �ƴϸ� true
//�� ������ ������ false
function isNotType(field, type){
	// field.value // ���ε� �Ϸ��� ���ϸ� xxx.PNG
	return (field.value.toLowerCase().indexOf(type) == -1);
}


