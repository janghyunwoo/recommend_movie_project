function bye() {
	var pw = prompt("��й�ȣ");
	if (pw != null && pw != "") {
		location.href = "MemberByeController?im_pw=" + pw;
	}
}

function deleteMsg(im_no) {
	var ok = confirm("��¥?");
	if (ok) {
		location.href = "MsgDeleteController?im_no=" + im_no;
	}
}

function deleteSNS(is_no) {
	var ok = confirm("��¥?");
	if (ok) {
		location.href = "SNSDeleteController?is_no=" + is_no;
	}
}

function goJoin() {
	
	location.href = "MemberJoinController";
}

function goUpdateMember() {
	location.href = "MemberUpdateController";
}

function logout() {

	var ok = confirm("��¥?");
	if (ok) {
		location.href = "MemberLogoutController";
	}
}

function searchSNS(is_owner) {
	location.href = "SNSSearchController?is_owner=" + is_owner;
}

function sendMsg(im_to) {
	var im_txt = prompt(im_to);

	if (im_txt != null && im_txt != "" && im_txt.length <= 150) {
		location.href = "MsgSendController?im_to=" + im_to + "&im_txt="
				+ im_txt;
	}
}

function updateSNS(is_no) {
	var is_txt = prompt("������ ����");

	if (is_txt != null && is_txt != "" && is_txt.length <= 150) {
		location.href = "SNSUpdateController?is_no=" + is_no + "&is_txt="
				+ is_txt;
	}
}
