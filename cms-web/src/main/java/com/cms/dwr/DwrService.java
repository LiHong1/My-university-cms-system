package com.cms.dwr;

import javax.inject.Inject;

import com.cms.service.IndexPicService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.cms.entity.Attachment;
import com.cms.service.AttachmentService;
import com.cms.service.GroupService;

@RemoteProxy(name="dwrService")
public class DwrService implements IDwrService{
    @Inject
	private GroupService groupService;
    @Inject
    private AttachmentService attachmentService;
	@Inject
	private IndexPicService indexPicService;
	@RemoteMethod
	public void addGroupChannel(int gid, int cid) {
		groupService.addGroupChannel(gid, cid);
	}

	@RemoteMethod
	public void deleteGroupChannel(int gid, int cid) {
		groupService.deleteGroupChannel(gid, cid);
	}

	@RemoteMethod
    public void changeIsAttach(long aId) {
        attachmentService.changeIsAttach(aId);
    }
	@RemoteMethod
    public void changeIsIndex(long aId) {
       attachmentService.changeIsIndex(aId);
    }
	@RemoteMethod
    public void deleteAttach(long id) {
        attachmentService.delete(id);
    }
	@RemoteMethod
	public void updatePicPos(Long id, int oldPos, int newPos){
		System.out.println("============in sdf");
		indexPicService.updatePicPos(id,oldPos,newPos);
	}

}
