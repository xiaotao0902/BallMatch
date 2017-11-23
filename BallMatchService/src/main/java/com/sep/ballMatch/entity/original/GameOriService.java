package com.sep.ballMatch.entity.original;

import java.util.ArrayList;
import java.util.List;

import com.sep.ballMatch.entity.GameProcess;
import com.sep.ballMatch.entity.Status;

public class GameOriService {
	
	public GameProcess detailOriginal(GameOriProcess gop) {
		int ball = 1;
		int noBall = 0;
		GameProcess gp = new GameProcess();
		List<Status> status_list = new ArrayList<Status>();
		
		if(gop != null) {
			List<OriStatus> oriStatus_list = gop.getData();
			for(int i = 0; i < 16; i++) {
				Status status = new Status();
				status.setStatus(noBall);
				status.setX(0);
				status.setY(0);
				for(OriStatus os : oriStatus_list) {
					if(String.valueOf(i).equals(os.get_class())) {
						int x = Integer.parseInt(os.getRight()) - 48;
						int y = -(Integer.parseInt(os.getBottom()) - 565);
						status.setStatus(ball);
						status.setX(x);
						status.setY(y);
					}
				}
				status_list.add(status);
			}
			gp.setData(status_list);
		}
		return gp;
	}
}
