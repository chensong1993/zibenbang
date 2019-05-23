package com.zhiyi.chinaipo.ui.category;

import java.io.Serializable;
import java.util.List;

/**
 * 首页 点击加号后的栏目
 */
public class ColumnItem implements Serializable {
    private List<Results> results;

    public List<Results> setResults(List<Results> results) {
        this.results = results;
        return results;
    }

    public List<Results> getResults() {
        return this.results;
    }

    public class Results {
        private int id;

        private String name;

        /*public Results() {
        }

        public Results(int id, String name) {
            this.id = id;
            this.name = name;
        }*/

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return "Results{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    /*private static final long serialVersionUID = -6465237897027410019L;
	
	public int id;
	
	public String name;
	
	public Integer orderId;
	
	public Integer selected;

	public ColumnItem() {}

	public ColumnItem(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public ColumnItem(int id, String name, int orderId, int selected) {
		this.id = id;
		this.name = name;
		this.orderId = Integer.valueOf(orderId);
		this.selected = Integer.valueOf(selected);
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getOrderId() {
		return this.orderId.intValue();
	}

	public Integer getSelected() {
		return this.selected;
	}

	public void setId(int paramInt) {
		this.id = paramInt;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setOrderId(int paramInt) {
		this.orderId = Integer.valueOf(paramInt);
	}

	public void setSelected(Integer paramInteger) {
		this.selected = paramInteger;
	}

	public String toString() {
		return "ColumnItem [id=" + this.id + ", name=" + this.name + ", selected=" + this.selected + "]";
	}
	*/
}