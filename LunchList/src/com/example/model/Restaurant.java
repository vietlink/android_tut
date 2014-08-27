package com.example.model;

public class Restaurant {
		private String name="";
		private String addr="";
		private String type="";
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String toString() {
			return (getName());
		};
	}
