//package com.cms.util;
//import com.cms.beans.BackupFile;
//import com.cms.utils.BackupFileUtil;
//import org.junit.Test;
//
//public class TestBackup {
//
//	@Test
//	public void testList() {
//		BackupFileUtil bfu = BackupFileUtil.getInstance("l:\\cms\\cms-commons");
//		for(BackupFile bf:bfu.listBackups()) {
//			System.out.println(bf);
//		}
//	}
//
//	@Test
//	public void testBackup() {
//		BackupFileUtil.getInstance("l:\\cms").backup("测试备份");
//	}
//
//	@Test
//	public void testDelete() {
//		BackupFileUtil.getInstance("E:\\论文和项目\\附中").delete("1377609125956_测试备份.tar.gz");
//	}
//
//	@Test
//	public void testResume() {
//		BackupFileUtil.getInstance("E:\\论文和项目\\附中").resume("1377610223607_测试备份.tar.gz");
//	}
//}
