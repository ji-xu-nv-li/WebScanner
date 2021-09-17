package com.wsbc.selenium.ttjj.task;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.wsbc.entity.ttjj.Fund;
import com.wsbc.selenium.operation.system.GetDataOperation;
import com.wsbc.selenium.result.Result;
import com.wsbc.selenium.util.ParseSelectorUtil;

/**
 * 获取自选基金的数据
 */
public class GetCustomizeFundData2 extends GetDataOperation<List<Fund>> {

	@Override
	public Result doHandler() {
		List<Fund> data = new ArrayList<Fund>();
		WebElement tbody = ParseSelectorUtil.findElement("//tbody[@data-listidx='0']");
		List<WebElement> trs = tbody.findElements(By.tagName("tr"));
		for (WebElement tr : trs) {
			Fund fund = new Fund();
			fund.setFundCode(tr.findElement(By.xpath("td[1]//a[@class='code']")).getText());
			fund.setFundName(tr.findElement(By.xpath("td[1]//p//a")).getAttribute("title"));
			fund.setEstimates(tr.findElement(By.xpath("td[3]")).getText());
			fund.setEstimatesPercent(tr.findElement(By.xpath("td[4]")).getText());
			fund.setUnitValue(tr.findElement(By.xpath("td[5]/p[1]")).getText());
			fund.setDate(tr.findElement(By.xpath("td[5]/p[2]")).getText().replaceAll("[-/.]", ""));
			fund.setNetValue(tr.findElement(By.xpath("td[6]")).getText());
			fund.setDayGrowth(tr.findElement(By.xpath("td[7]")).getText());
			fund.setDayGrowthRate(tr.findElement(By.xpath("td[8]")).getText());
			data.add(fund);
		}
		Result result = new Result(Result.SUCCESS.getReturnCode());
		result.getData().put("data", data);
		if (data != null && data.size() > 0) {
			System.out.println(String.format("%s\t%s\t%15s\t%s\t%s\t%s\t%s\t%s\t%s\t", "日期", "基金代码", "基金名称", "净值估算",
					"估算涨幅", "单位净值", "累计净值", "日增长值", "日增长率"));
			for(Fund fund : data) {
				System.out.println(String.format("%s\t%s\t%15s\t%s\t%s\t%s\t%s\t%s\t%s\t",
						fund.getDate(), fund.getFundCode(), fund.getFundName(), fund.getEstimates(), fund.getEstimatesPercent(),
						fund.getUnitValue(), fund.getNetValue(), fund.getDayGrowth(), fund.getDayGrowthRate()));
			}
		}
		return result;
	}

}
