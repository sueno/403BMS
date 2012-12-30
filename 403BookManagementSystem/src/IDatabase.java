public interface IDatabase {

	// ISBN����͌�A�{�̏���\�����A�o�^
	boolean addBook(String ISBN);

	// ISBN���猟�����ꂽ�{���폜����
	boolean rmBook(String ISBN);

	// ���������ꗗ����폜����{��T��
	boolean rmBookList(String[] books);

	// ISBN�Ǝ�����ID����͂��A�؂��
	boolean bBook(String ISBN);

	// ISBN�Ǝ�����ID����͂��A�Ԃ�
	boolean rBook(String ISBN);

	// �����L�[���[�h�Ƀq�b�g�����{�̏���\������
	String[] searchDB(String key);

	// �f�[�^�x�[�X�œo�^���ꂽ�{�̈ꗗ��\������
	String[] listDB();

	// �݂��o���󋵂�\������B
	String[] listStatus(int mode);
}
