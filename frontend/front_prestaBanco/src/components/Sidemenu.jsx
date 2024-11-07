import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import HomeIcon from "@mui/icons-material/Home";
import { useNavigate } from "react-router-dom";
import PlusIcon from '@mui/icons-material/Add';
import SupervisedUserCircleIcon from '@mui/icons-material/SupervisedUserCircle';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import CalculateIcon from '@mui/icons-material/Calculate';
import AnalyticsIcon from '@mui/icons-material/Analytics';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';

export default function Sidemenu({ open, toggleDrawer }) {
  const navigate = useNavigate();

  const listOptions = () => (
    <Box
      role="presentation"
      onClick={toggleDrawer(false)}
    >
      <List>
        <ListItemButton onClick={() => navigate("/home")}>
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary="Home" />
        </ListItemButton>

        <Divider />
        <ListItemButton onClick={() => navigate("/user/register")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Registrar o editar usuarios" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/user/list")}>
          <ListItemIcon>
          <SupervisedUserCircleIcon/>
          </ListItemIcon>
          <ListItemText primary="Ver Usuarios" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/aplication")}>
          <ListItemIcon>
          <AccountBalanceIcon/>
          </ListItemIcon>
          <ListItemText primary="Solicitar Credito" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/simulation")}>
          <ListItemIcon>
          <AnalyticsIcon/>
          </ListItemIcon>
          <ListItemText primary="Simular credito" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/aplication/list")}>
          <ListItemIcon>
          <AttachMoneyIcon/>
          </ListItemIcon>
          <ListItemText primary="Ver solicitudes" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/aplication/totalCost")}>
          <ListItemIcon>
          <CalculateIcon/>
          </ListItemIcon>
          <ListItemText primary="Calcular Costo Total Solicitud" />
        </ListItemButton>
        
        
      </List>
      <Divider />
    </Box>
  );

  return (
    <div>
      <Drawer anchor={"left"} open={open} onClose={toggleDrawer(false)}>
        {listOptions()}
      </Drawer>
    </div>
  );
}
