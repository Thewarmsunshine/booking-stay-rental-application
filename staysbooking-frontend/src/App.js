import { Layout, Dropdown, Menu, Button } from "antd";
import { UserOutlined } from "@ant-design/icons";
import React from "react";
import LoginPage from "./components/LoginPage";
import HostHomePage from "./components/HostHomePage";
import GuestHomePage from "./components/GuestHomePage";

const { Header, Content } = Layout;

//1创建class
class App extends React.Component {
  //2.维护状态：登陆与否，以什么状态登陆
  state = {
    authed: false,
    asHost: false,
  }

  //在第一次render之后会执行
  componentDidMount(){
    const authToken = localStorage.getItem("authToken"); //检查是否为空，是否valid（但这里没做这个）
    const asHost = localStorage.getItem("asHost") === "true";
    this.setState({ //state更新后，会re-render一下
      authed: authToken !== null,
      asHost,
    });
  }

  //登陆成功后
  handleLoginSuccess = (token, asHost) => {
    localStorage.setItem("authToken", token);
    localStorage.setItem("asHost", asHost);
    this.setState({
      authed: true,
      asHost,
    });
  };

  handleLogOut = () => {
    localStorage.removeItem("authToken");
    localStorage.removeItem("asHost");
    this.setState({
      authed: false,
    });
  };

  renderContent = () => {
    if (!this.state.authed) {
      return <LoginPage handleLoginSuccess={this.handleLoginSuccess} />; //early return //之后定义成一个个component
    }

    if (this.state.asHost) {
      return <HostHomePage />;
    }

    return <GuestHomePage />;
  };


  userMenu = (
      <Menu>
        <Menu.Item key="logout" onClick={this.handleLogOut}>
          Log Out
        </Menu.Item>
      </Menu>
  );

  render() {
    return (
        <Layout style={{ height: "100vh" }}>
          <Header style={{ display: "flex", justifyContent: "space-between" }}>
            <div style={{ fontSize: 16, fontWeight: 600, color: "white" }}>
              Stays Booking
            </div>
            {this.state.authed && (
                <div>
                  <Dropdown trigger="click" overlay={this.userMenu}>
                    <Button icon={<UserOutlined />} shape="circle" />
                  </Dropdown>
                </div>
            )}
          </Header>
          <Content
              style={{ height: "calc(100% - 64px)", margin: 20, overflow: "auto" }}
          >
            {this.renderContent()}
          </Content>
        </Layout>
    );
  }
}
export default App;
