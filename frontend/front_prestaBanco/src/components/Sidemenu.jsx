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
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Ver Usuarios" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/aplication")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Solicitar Credito" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/simulation")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Simular credito" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/aplication/list")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Ver solicitudes" />
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
