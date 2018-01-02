package com.sep.ballMatch.service;

import java.util.ArrayList;
import java.util.List;

import com.sep.ballMatch.common.PropUtil;
import com.sep.ballMatch.entity.GameProcess;
import com.sep.ballMatch.entity.Status;
import com.sep.ballMatch.entity.original.GameOriProcess;
import com.sep.ballMatch.entity.original.OriStatus;

public class MatchOriDataService {
	
	public GameProcess detailOriginal(GameOriProcess gop) {
		
		int right_position = Integer.parseInt(PropUtil.getProperty("right_position"));
		int bottom_position = Integer.parseInt(PropUtil.getProperty("bottom_position"));
		double prob_standard = Double.parseDouble(PropUtil.getProperty("prob_standard"));//if prob less than 0.55 it's not a ball .
		
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
						double prob = Double.parseDouble(os.getProb());
						if(prob > prob_standard) {
							int x = Integer.parseInt(os.getRight()) - right_position;
							int y = -(Integer.parseInt(os.getBottom()) - bottom_position);
							status.setStatus(ball);
							status.setX(x);
							status.setY(y);
						}
					}
				}
				status_list.add(status);
			}
			gp.setData(status_list);
		}
		return gp;
	}
}
