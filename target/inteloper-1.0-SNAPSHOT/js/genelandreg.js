alert("gene");

function addRow(tableId) {
    var testTbl = $("#" + tableId);
    //���һ��
    var newTr = testTbl.insertRow();
    //�������
    var newTd0 = newTr.insertCell();
    var newTd1 = newTr.insertCell();
    //���������ݺ�����
    newTd0.innerHTML = '<input type=checkbox id="box4">';

    newTd2.innerText = '�¼���';
}